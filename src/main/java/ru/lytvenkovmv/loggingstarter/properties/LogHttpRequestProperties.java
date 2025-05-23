package ru.lytvenkovmv.loggingstarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "logging-starter.log-http-request")
public class LogHttpRequestProperties {
    private List<String> noLogUriList = new ArrayList<>();
    private List<String> maskedHeaders = new ArrayList<>();
    private List<String> maskedFields = new ArrayList<>();
    private Integer bodyMaxLength = 10000;

    public List<String> getNoLogUriList() {
        return noLogUriList;
    }

    public void setNoLogUriList(List<String> noLogUriList) {
        this.noLogUriList = noLogUriList;
    }

    public List<String> getMaskedHeaders() {
        return maskedHeaders;
    }

    public void setMaskedHeaders(List<String> maskedHeaders) {
        this.maskedHeaders = maskedHeaders;
    }

    public List<String> getMaskedFields() {
        return maskedFields;
    }

    public void setMaskedFields(List<String> maskedFields) {
        this.maskedFields = maskedFields;
    }

    public Integer getBodyMaxLength() {
        return bodyMaxLength;
    }

    public void setBodyMaxLength(Integer bodyMaxLength) {
        this.bodyMaxLength = bodyMaxLength;
    }
}
