package ru.lytvenkovmv.loggingstarter.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServletRequestUtil {

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
                    String value = entry.getValue();

                    if (maskedHeaders.contains(header)) {
                        value = "*".repeat(value.length());
                    }

                    return header + "=" + value;
                })
                .collect(Collectors.joining(", "));
    }
}
