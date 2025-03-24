package ru.lytvenkovmv.loggingstarter.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.ContentCachingResponseWrapper;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoggingFilter extends HttpFilter {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);
    @Autowired
    private LogHttpRequestProperties logHttpRequestProperties;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean isNoLogUri = ServletRequestUtil.isNoLogUri(request.getRequestURI(), logHttpRequestProperties.getNoLogUriList());

        if (isNoLogUri) {
            super.doFilter(request, response, chain);

            return;
        }

        String method = request.getMethod();
        String requestURI = request.getRequestURI() + ServletRequestUtil.formatQueryString(request);
        String headers = "headers={" + ServletRequestUtil.maskHeaders(request, logHttpRequestProperties.getMaskedHeaders()) + "}";

        log.info("Поступил запрос: {} {} {}", method, requestURI, headers);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            super.doFilter(request, responseWrapper, chain);

            String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            log.info("Ответ: {} {} {} {}", method, requestURI, response.getStatus(), responseBody);
        } finally {
            responseWrapper.copyBodyToResponse();
        }
    }
}
