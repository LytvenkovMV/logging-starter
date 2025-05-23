package ru.lytvenkovmv.loggingstarter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.lytvenkovmv.loggingstarter.aspect.LoggingAspect;
import ru.lytvenkovmv.loggingstarter.controller.LoggingRequestBodyAdvice;
import ru.lytvenkovmv.loggingstarter.filter.LoggingFilter;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
import ru.lytvenkovmv.loggingstarter.service.AbstractChain;
import ru.lytvenkovmv.loggingstarter.service.AbstractService;
import ru.lytvenkovmv.loggingstarter.service.impl.MaskingRequestBodyService;
import ru.lytvenkovmv.loggingstarter.service.impl.RequestBodyChain;
import ru.lytvenkovmv.loggingstarter.service.impl.TrimmingRequestBodyService;
import ru.lytvenkovmv.loggingstarter.service.impl.WritingRequestBodyService;
import ru.lytvenkovmv.loggingstarter.util.ServletRequestUtil;

@AutoConfiguration
@ConditionalOnProperty(prefix = "logging-starter",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties({LogHttpRequestProperties.class})
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
    @ConditionalOnProperty(prefix = "logging-starter.log-http-request",
            value = "log-body-enabled",
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
    @ConditionalOnBean(LoggingRequestBodyAdvice.class)
    public AbstractChain<String> requestBodyChain() {
        return new RequestBodyChain();
    }

    @Bean
    @ConditionalOnBean(LoggingRequestBodyAdvice.class)
    public AbstractService<String> writingRequestBodyService() {
        return new WritingRequestBodyService();
    }

    @Bean
    @ConditionalOnBean(LoggingRequestBodyAdvice.class)
    public AbstractService<String> maskingRequestBodyService() {
        return new MaskingRequestBodyService();
    }

    @Bean
    @ConditionalOnBean(LoggingRequestBodyAdvice.class)
    public AbstractService<String> trimmingRequestBodyService() {
        return new TrimmingRequestBodyService();
    }
}
