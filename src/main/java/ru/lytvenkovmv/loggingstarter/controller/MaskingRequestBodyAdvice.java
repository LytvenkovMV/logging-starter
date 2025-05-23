package ru.lytvenkovmv.loggingstarter.controller;

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
import java.util.Objects;

@ControllerAdvice
@Order(20)
public class MaskingRequestBodyAdvice extends RequestBodyAdviceAdapter {
    @Autowired
    private ServletRequestUtil util;
    @Autowired
    private LogHttpRequestProperties properties;
    @Autowired
    private BodyLoggingContext context;

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        String maskedBody = (String) util.maskBody(context.get(), properties.getMaskedFields());
        context.set(maskedBody);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.nonNull(context.get());
    }
}
