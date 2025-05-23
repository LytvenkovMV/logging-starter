package ru.lytvenkovmv.loggingstarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "logging-starter.log-http-request")
public class LogHttpRequestProperties {
    private List<String> noLogUriList = new ArrayList<>();
    private List<String> maskedHeaders = new ArrayList<>();

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
}
