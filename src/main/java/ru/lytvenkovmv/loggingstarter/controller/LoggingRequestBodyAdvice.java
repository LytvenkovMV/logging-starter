package ru.lytvenkovmv.loggingstarter.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import ru.lytvenkovmv.loggingstarter.properties.AbstractLogBodyProperties;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
import ru.lytvenkovmv.loggingstarter.properties.LogRequestBodyProperties;
import ru.lytvenkovmv.loggingstarter.service.AbstractChain;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

import java.lang.reflect.Type;

@ControllerAdvice
public class LoggingRequestBodyAdvice extends RequestBodyAdviceAdapter {
    private final Logger log = LoggerFactory.getLogger(LoggingRequestBodyAdvice.class);
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ServletRequestUtil util;
    @Autowired
    private AbstractChain<String, AbstractLogBodyProperties> chain;
    @Autowired
    private LogHttpRequestProperties requestProperties;
    @Autowired
    private LogRequestBodyProperties bodyProperties;

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        String method = request.getMethod();
        String requestURI = request.getRequestURI() + util.formatQueryString(request);
        String strBody = util.writeBodyAsString(body);

        String processedBody = chain.init().process(strBody, bodyProperties, chain);

        log.info("Тело запроса: {} {} {}", method, requestURI, processedBody);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>>
            converterType) {
        return !util.isNoLogUri(request.getRequestURI(), requestProperties.getNoLogUriList());
    }
}
