package ru.lytvenkovmv.loggingstarter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServletRequestUtil {
    private static final String MASK = "***";
    private final Logger log = LoggerFactory.getLogger(ServletRequestUtil.class);
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private ObjectMapper objectMapper;

    public boolean isNoLogUri(String uri, List<String> noLogUriList) {
        for (String noLogUri : noLogUriList) {
            if (antPathMatcher.match(noLogUri, uri)) {
                return true;
            }
        }

        return false;
    }

    public String formatQueryString(HttpServletRequest request) {

        return Optional.ofNullable(request.getQueryString())
                .map(qs -> "?" + qs)
                .orElse(Strings.EMPTY);
    }

    public String maskHeaders(HttpServletRequest request, List<String> maskedHeaders) {
        Map<String, String> headersMap = Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(it -> it, request::getHeader));

        return headersMap.entrySet().stream()
                .map(entry -> {
                    boolean isMasked = maskedHeaders.stream()
                            .map(String::toLowerCase)
                            .anyMatch(h -> h.contains(entry.getKey().toLowerCase()));

                    return entry.getKey() + "=" + (isMasked ? MASK : entry.getValue());
                })
                .collect(Collectors.joining(", "));
    }

    public String maskBody(String body, List<String> maskedPaths) {
        try {
            JsonNode jsonNode = objectMapper.readTree(body);

            Configuration configuration = Configuration.builder()
                    .jsonProvider(new JacksonJsonNodeJsonProvider())
                    .build();
            DocumentContext documentContext = JsonPath.using(configuration).parse(jsonNode);

            for (String path : maskedPaths) {
                try {
                    documentContext.map(path, (o, conf) -> MASK);
                } catch (PathNotFoundException e) {
                    log.warn("Не удалось маскировать поле в теле запроса или ответа. Путь {} не найден", path);
                }
            }

            return documentContext.jsonString();
        } catch (Exception e) {
            log.warn("Не удалось маскировать тело запроса или ответа {}", e.getMessage(), e);

            return body;
        }
    }

    public String trimBody(String body, int bodyMaxLength) {
        return body.length() > bodyMaxLength
                ? body.substring(0, bodyMaxLength)
                : body;
    }

    public String writeBodyAsString(Object body) {
        try {
            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            log.warn("Не удалось сериализовать тело запроса или ответа {}", e.getMessage(), e);
        }

        return null;
    }
}
