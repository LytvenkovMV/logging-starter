package ru.lytvenkovmv.loggingstarter.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.AntPathMatcher;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServletRequestUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String MASK = "***";

    public static boolean isNoLogUri(String uri, List<String> noLogUriList) {
        AntPathMatcher matcher = new AntPathMatcher();

        for (String noLogUri : noLogUriList) {
            if (matcher.match(noLogUri, uri)) {
                return true;
            }
        }

        return false;
    }

    public static String formatQueryString(HttpServletRequest request) {

        return Optional.ofNullable(request.getQueryString())
                .map(qs -> "?" + qs)
                .orElse(Strings.EMPTY);
    }

    public static String maskHeaders(HttpServletRequest request, List<String> maskedHeaders) {
        Map<String, String> headersMap = Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(it -> it, request::getHeader));

        return headersMap.entrySet().stream()
                .map(entry -> {
                    String header = entry.getKey();
                    String value = MASK;

                    boolean isMasked = maskedHeaders.stream()
                            .map(String::toLowerCase)
                            .anyMatch(h -> h.contains(header.toLowerCase()));

                    if (!isMasked) {
                        value = entry.getValue();
                    }

                    return header + "=" + value;
                })
                .collect(Collectors.joining(", "));
    }

    public static String maskBody(Object body, List<String> maskedPaths) throws Exception {
        String bodyJson = OBJECT_MAPPER.writeValueAsString(body);

        Configuration configuration = Configuration.builder()
                .jsonProvider(new JacksonJsonNodeJsonProvider())
                .build();

        DocumentContext documentContext = JsonPath.using(configuration).parse(bodyJson);

        maskedPaths.forEach(path -> documentContext.map(path, (o, conf) -> MASK));

        return documentContext.jsonString();
    }
}
