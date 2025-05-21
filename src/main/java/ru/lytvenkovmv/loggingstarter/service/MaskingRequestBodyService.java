package ru.lytvenkovmv.loggingstarter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

import java.util.Objects;

@Service
@Order(20)
public class MaskingRequestBodyService extends AbstractChainService {
    @Autowired
    private ServletRequestUtil util;
    @Autowired
    private LogHttpRequestProperties properties;

    @Override
    public String doAction(String body, AbstractChain chain) {
        if (Objects.isNull(body) || Objects.isNull(chain)) {
            return body;
        }

        String maskedBody = util.maskBody(body, properties.getMaskedFields());

        return super.doAction(maskedBody, chain);
    }
}
