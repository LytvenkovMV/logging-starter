package ru.lytvenkovmv.loggingstarter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
                        value = "***";
                    }

                    return header + "=" + value;
                })
                .collect(Collectors.joining(", "));
    }

    public static String maskBody(Object body, List<String> maskedFields) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.valueToTree(body);

        maskedFields.forEach(f -> maskFieldRecursive(jsonNode, f));

        return jsonNode.toString();
    }

    private static void maskFieldRecursive(JsonNode jsonNode, String maskedField) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;

            if (objectNode.has(maskedField)) {
                objectNode.put(maskedField, "***");
            }

            objectNode.fields()
                    .forEachRemaining(entry -> maskFieldRecursive(entry.getValue(), maskedField));
        }
    }
}
