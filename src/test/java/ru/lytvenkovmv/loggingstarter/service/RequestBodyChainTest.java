package ru.lytvenkovmv.loggingstarter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lytvenkovmv.loggingstarter.LoggingStarterAutoConfiguration;

@SpringBootTest(classes = {LoggingStarterAutoConfiguration.class, ObjectMapper.class})
class RequestBodyChainTest {
    @Autowired
    AbstractChain<String> chain;

    @Test
    void test() {
        JSONObject json = new JSONObject();
        json.appendField("name", "John")
                .appendField("surname", "Doe")
                .appendField("age", 25)
                .appendField("active", true);

        String processedBody = chain.process(json.toString(), chain);

        System.out.println(processedBody);
    }
}