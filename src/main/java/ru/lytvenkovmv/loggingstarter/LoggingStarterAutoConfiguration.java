package ru.lytvenkovmv.loggingstarter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.lytvenkovmv.loggingstarter.aspect.LoggingAspect;
import ru.lytvenkovmv.loggingstarter.controller.LoggingRequestBodyAdvice;
import ru.lytvenkovmv.loggingstarter.filter.LoggingFilter;
import ru.lytvenkovmv.loggingstarter.properties.AbstractLogBodyProperties;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
import ru.lytvenkovmv.loggingstarter.properties.LogRequestBodyProperties;
import ru.lytvenkovmv.loggingstarter.properties.LogResponseBodyProperties;
import ru.lytvenkovmv.loggingstarter.service.AbstractChain;
import ru.lytvenkovmv.loggingstarter.service.AbstractService;
import ru.lytvenkovmv.loggingstarter.service.impl.BodyChain;
import ru.lytvenkovmv.loggingstarter.service.impl.BodyMaskingService;
import ru.lytvenkovmv.loggingstarter.service.impl.BodyTrimmingService;
import ru.lytvenkovmv.loggingstarter.service.impl.BodyWritingService;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

@AutoConfiguration
@ConditionalOnProperty(prefix = "logging-starter",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties({LogHttpRequestProperties.class,
        LogRequestBodyProperties.class,
        LogResponseBodyProperties.class})
public class LoggingStarterAutoConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "logging-starter.log-execution-time",
            value = "enabled",
            havingValue = "true",
            matchIfMissing = true)
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    @ConditionalOnProperty(prefix = "logging-starter.log-http-request",
            value = "enabled",
            havingValue = "true",
            matchIfMissing = true)
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

    @Bean
    @ConditionalOnProperty(prefix = "logging-starter.log-request-body",
            value = "enabled",
            havingValue = "true")
    @ConditionalOnBean(LoggingFilter.class)
    public LoggingRequestBodyAdvice loggingRequestBodyAdvice() {
        return new LoggingRequestBodyAdvice();
    }

    @Bean
    @ConditionalOnBean(LoggingFilter.class)
    public ServletRequestUtil servletRequestUtil() {
        return new ServletRequestUtil();
    }

    @Bean
    @ConditionalOnBean(LoggingFilter.class)
    public AbstractChain<String, AbstractLogBodyProperties> bodyChain() {
        return new BodyChain();
    }

    @Bean
    @ConditionalOnBean(LoggingFilter.class)
    public AbstractService<String, AbstractLogBodyProperties> bodyWritingService() {
        return new BodyWritingService();
    }

    @Bean
    @ConditionalOnBean(LoggingFilter.class)
    public AbstractService<String, AbstractLogBodyProperties> bodyMaskingService() {
        return new BodyMaskingService();
    }

    @Bean
    @ConditionalOnBean(LoggingFilter.class)
    public AbstractService<String, AbstractLogBodyProperties> bodyTrimmingService() {
        return new BodyTrimmingService();
    }
}
