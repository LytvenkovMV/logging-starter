package ru.lytvenkovmv.loggingstarter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.lytvenkovmv.loggingstarter.properties.AbstractLogBodyProperties;
import ru.lytvenkovmv.loggingstarter.service.AbstractChain;
import ru.lytvenkovmv.loggingstarter.service.AbstractService;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

import java.util.Objects;

@Service
@Order(20)
public class BodyMaskingService extends AbstractService<String, AbstractLogBodyProperties> {
    @Autowired
    private ServletRequestUtil util;

    @Override
    public String doAction(String body, AbstractLogBodyProperties properties, AbstractChain<String, AbstractLogBodyProperties> chain) {
        if (Objects.isNull(body) || Objects.isNull(chain)) {
            return body;
        }

        String maskedBody = util.maskBody(body, properties.getMaskedFields());

        return super.doAction(maskedBody, properties, chain);
    }
}
