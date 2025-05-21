package ru.lytvenkovmv.loggingstarter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

import java.util.Objects;

@Service
@Order(30)
public class TrimmingRequestBodyService extends AbstractChainService {
    @Autowired
    private ServletRequestUtil util;
    @Autowired
    private LogHttpRequestProperties properties;

    @Override
    public String doAction(String body, AbstractChain chain) {
        if (Objects.isNull(body) || Objects.isNull(chain)) {
            return body;
        }

        String trimmedBody = util.trimBody(body, properties.getBodyMaxLength());

        return super.doAction(trimmedBody, chain);
    }
}
