package com.samsung.erp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.samsung.erp.entities.Currency;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestServiceTest {

    @Autowired
    TestService testService;

    @Test
    void testRestTemplate() {
        ResponseEntity responseEntity = testService.testRestTemplate();

        ObjectMapper mapper = new ObjectMapper();
        try {
            Currency[] currencies = mapper.readValue(responseEntity.getBody().toString(), Currency[].class);

            System.out.println(currencies[0].getCode());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}