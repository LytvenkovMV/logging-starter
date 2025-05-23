package ru.lytvenkovmv.loggingstarter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lytvenkovmv.loggingstarter.LoggingStarterAutoConfiguration;
import ru.lytvenkovmv.loggingstarter.properties.AbstractLogBodyProperties;
import ru.lytvenkovmv.loggingstarter.properties.LogRequestBodyProperties;

@SpringBootTest(classes = {LoggingStarterAutoConfiguration.class, ObjectMapper.class})
class BodyChainTest {
    @Autowired
    AbstractChain<String, AbstractLogBodyProperties> chain;
    @Autowired
    LogRequestBodyProperties properties;

    @Test
    void test() {
        JSONObject json = new JSONObject();
        json.appendField("name", "John")
                .appendField("surname", "Doe")
                .appendField("age", 25)
                .appendField("active", true);

        String processedBody = chain.process(json.toString(), properties, chain);

        System.out.println(processedBody);
    }
}