package ru.lytvenkovmv.loggingstarter.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.lytvenkovmv.loggingstarter.properties.AbstractLogBodyProperties;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
import ru.lytvenkovmv.loggingstarter.service.AbstractChain;
import ru.lytvenkovmv.loggingstarter.service.AbstractService;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

import java.util.Objects;

@Service
@Order(10)
public class BodyWritingService extends AbstractService<String, AbstractLogBodyProperties> {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ServletRequestUtil util;
    @Autowired
    private LogHttpRequestProperties logHttpRequestProperties;

    @Override
    public String doAction(String body, AbstractLogBodyProperties properties, AbstractChain<String, AbstractLogBodyProperties> chain) {
        if (Objects.isNull(body) || Objects.isNull(chain)) {
            return body;
        }

        if (!util.isNoLogUri(request.getRequestURI(), logHttpRequestProperties.getNoLogUriList())) {
            return super.doAction(body, properties, chain);
        } else {
            return body;
        }
    }
}
