package ru.lytvenkovmv.loggingstarter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.lytvenkovmv.loggingstarter.aspect.LoggingAspect;
import ru.lytvenkovmv.loggingstarter.context.BodyLoggingContext;
import ru.lytvenkovmv.loggingstarter.controller.ClearingContextAdvice;
import ru.lytvenkovmv.loggingstarter.controller.LoggingRequestBodyAdvice;
import ru.lytvenkovmv.loggingstarter.controller.MaskingRequestBodyAdvice;
import ru.lytvenkovmv.loggingstarter.controller.WritingRequestBodyAdvice;
import ru.lytvenkovmv.loggingstarter.filter.LoggingFilter;
import ru.lytvenkovmv.loggingstarter.properties.LogHttpRequestProperties;
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
    @ConditionalOnBean(LoggingFilter.class)
    public ServletRequestUtil servletRequestUtil() {
        return new ServletRequestUtil();
    }

    @Bean
    @ConditionalOnProperty(prefix = "logging-starter.log-http-request",
            value = "log-body-enabled",
            havingValue = "true")
    @ConditionalOnBean(LoggingFilter.class)
    public WritingRequestBodyAdvice writingRequestBodyAdvice() {
        return new WritingRequestBodyAdvice();
    }

    @Bean
    @ConditionalOnBean(WritingRequestBodyAdvice.class)
    public MaskingRequestBodyAdvice maskingRequestBodyAdvice() {
        return new MaskingRequestBodyAdvice();
    }

    @Bean
    @ConditionalOnBean(WritingRequestBodyAdvice.class)
    public LoggingRequestBodyAdvice loggingRequestBodyAdvice() {
        return new LoggingRequestBodyAdvice();
    }

    @Bean
    @ConditionalOnBean(WritingRequestBodyAdvice.class)
    public BodyLoggingContext bodyLoggingContext() {
        return new BodyLoggingContext();
    }

    @Bean
    @ConditionalOnBean(BodyLoggingContext.class)
    public ClearingContextAdvice clearingContextAdvice() {
        return new ClearingContextAdvice();
    }
}
