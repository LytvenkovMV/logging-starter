package ru.lytvenkovmv.loggingstarter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "logging-starter.logging")
public class LoggingProperties {
    private Boolean logExecutionTime = true;
    private Boolean logHttpRequest = true;
    private Boolean logHttpBody = true;
    private List<String> noLogUriList = new ArrayList<>();

    public Boolean getLogExecutionTime() {
        return logExecutionTime;
    }

    public void setLogExecutionTime(Boolean logExecutionTime) {
        this.logExecutionTime = logExecutionTime;
    }

    public Boolean getLogHttpRequest() {
        return logHttpRequest;
    }

    public void setLogHttpRequest(Boolean logHttpRequest) {
        this.logHttpRequest = logHttpRequest;
    }

    public Boolean getLogHttpBody() {
        return logHttpBody;
    }

    public void setLogHttpBody(Boolean logHttpBody) {
        this.logHttpBody = logHttpBody;
    }

    public List<String> getNoLogUriList() {
        return noLogUriList;
    }

    public void setNoLogUriList(List<String> noLogUriList) {
        this.noLogUriList = noLogUriList;
    }
}
