package ru.lytvenkovmv.loggingstarter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServletRequestUtil {
    private static final String MASK = "***";
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

    public String maskBody(Object body, List<String> maskedPaths) throws Exception {
        Configuration configuration = Configuration.builder()
                .jsonProvider(new JacksonJsonNodeJsonProvider())
                .build();

        String bodyJson = body instanceof String ? (String) body : objectMapper.writeValueAsString(body);

        DocumentContext documentContext = JsonPath.using(configuration).parse(bodyJson);

        maskedPaths.forEach(path -> documentContext.map(path, (o, conf) -> MASK));

        return documentContext.jsonString();
    }
}
