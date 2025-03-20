package ru.lytvenkovmv.loggingstarter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.lytvenkovmv.loggingstarter.aspect.LoggingAspect;
import ru.lytvenkovmv.loggingstarter.controller.LoggingRequestBodyAdvice;
import ru.lytvenkovmv.loggingstarter.filter.LoggingFilter;
import ru.lytvenkovmv.loggingstarter.properties.LoggingProperties;

@AutoConfiguration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingStarterAutoConfiguration {

    @Bean
    @Profile(value = "log-exec-time")
    @ConditionalOnProperty(value = "logging-starter.logging.log-http-request", havingValue = "true")
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

    @Bean
    @Profile(value = "log-exec-time")
    @ConditionalOnProperty(value = "logging-starter.logging.log-http-body", havingValue = "true")
    public LoggingRequestBodyAdvice loggingRequestBodyAdvice() {
        return new LoggingRequestBodyAdvice();
    }

    @Bean
    @Profile(value = "log-exec-time")
    @ConditionalOnProperty(value = "logging-starter.logging.log-execution-time", havingValue = "true")
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
