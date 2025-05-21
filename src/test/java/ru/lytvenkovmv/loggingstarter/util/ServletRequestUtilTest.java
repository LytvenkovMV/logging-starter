package ru.lytvenkovmv.loggingstarter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lytvenkovmv.loggingstarter.LoggingStarterAutoConfiguration;

import java.util.List;
import java.util.Objects;

@SpringBootTest(classes = {LoggingStarterAutoConfiguration.class, ObjectMapper.class})
class ServletRequestUtilTest {
    @Autowired
    ServletRequestUtil util;

    @Test
    void maskBodyTest() {
        String f1 = "$.body.active";
        String f2 = "$.body.page.sorts[*].field";

        JSONObject field1 = new JSONObject();
        field1.put("field", "val1");

        JSONObject field2 = new JSONObject();
        field2.put("field", "val2");

        JSONObject page = new JSONObject();
        page.put("sorts", List.of(field1, field2));

        JSONObject body = new JSONObject();
        body.appendField("active", true)
                .appendField("page", page);

        JSONObject json = new JSONObject();
        json.put("body", body);


        String maskedBody = util.maskBody(json.toString(), List.of(f1, f2));


        Assertions.assertTrue(Objects.nonNull(maskedBody));
        Assertions.assertTrue(maskedBody.contains("\"active\":\"***\""));
        Assertions.assertTrue(maskedBody.contains("\"field\":\"***\""));
    }
}