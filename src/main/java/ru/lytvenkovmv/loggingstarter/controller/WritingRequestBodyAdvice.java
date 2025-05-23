package ru.lytvenkovmv.loggingstarter.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import ru.lytvenkovmv.loggingstarter.context.BodyLoggingContext;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

import java.lang.reflect.Type;

@ControllerAdvice
@Order(10)
public class WritingRequestBodyAdvice extends RequestBodyAdviceAdapter {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ServletRequestUtil util;
    @Autowired
    private LogHttpRequestProperties properties;
    @Autowired
    private BodyLoggingContext context;

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (!util.isNoLogUri(request.getRequestURI(), properties.getNoLogUriList())) {
            String str = util.writeBodyAsString(body);
            context.set(str);
        }

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}
