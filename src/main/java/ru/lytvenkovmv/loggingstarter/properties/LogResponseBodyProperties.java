package ru.lytvenkovmv.loggingstarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "logging-starter.log-response-body")
public class LogResponseBodyProperties extends AbstractLogBodyProperties {
    private boolean enabled = false;
    private List<String> maskedFields = new ArrayList<>();
    private Integer bodyMaxLength = 10000;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
