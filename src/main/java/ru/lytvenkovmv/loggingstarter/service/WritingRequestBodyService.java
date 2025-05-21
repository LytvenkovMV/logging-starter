package ru.lytvenkovmv.loggingstarter.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

import java.util.Objects;

@Service
@Order(10)
public class WritingRequestBodyService extends AbstractChainService {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ServletRequestUtil util;
    @Autowired
    private LogHttpRequestProperties properties;

    @Override
    public String doAction(String body, AbstractChain chain) {
        if (Objects.isNull(body) || Objects.isNull(chain)) {
            return body;
        }

        if (!util.isNoLogUri(request.getRequestURI(), properties.getNoLogUriList())) {
            return super.doAction(body, chain);
        } else {
            return body;
        }
    }
}
