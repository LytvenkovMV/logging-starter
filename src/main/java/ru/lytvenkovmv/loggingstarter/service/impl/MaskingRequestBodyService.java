package ru.lytvenkovmv.loggingstarter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
import ru.lytvenkovmv.loggingstarter.service.AbstractChain;
import ru.lytvenkovmv.loggingstarter.service.AbstractService;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

import java.util.Objects;

@Service
@Order(20)
public class MaskingRequestBodyService extends AbstractService<String> {
    @Autowired
    private ServletRequestUtil util;
    @Autowired
    private LogHttpRequestProperties properties;

    @Override
    public String doAction(String body, AbstractChain<String> chain) {
        if (Objects.isNull(body) || Objects.isNull(chain)) {
            return body;
        }

        String maskedBody = util.maskBody(body, properties.getMaskedFields());

        return super.doAction(maskedBody, chain);
    }
}
