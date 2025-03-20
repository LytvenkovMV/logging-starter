package ru.lytvenkovmv.loggingstarter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ru.lytvenkovmv.loggingstarter.aspect.LoggingAspect;
import ru.lytvenkovmv.loggingstarter.filter.LoggingFilter;

@AutoConfiguration
public class LoggingStarterAutoConfiguration {

    @Bean
    @Profile(value = "log-exec-time")
    @ConditionalOnProperty(value = "link-shortener.logging.log-execution-time", havingValue = "true")
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

    @Bean
    @Profile(value = "log-exec-time")
    @ConditionalOnProperty(value = "link-shortener.logging.log-execution-time", havingValue = "true")
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
