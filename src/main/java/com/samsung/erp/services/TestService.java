package com.samsung.erp.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@Transactional
public class TestService {
    public ResponseEntity testRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        String targetUrl = "https://api.stdio.vn/mini-apps/rates";
        ResponseEntity<String> resp = restTemplate.getForEntity(targetUrl, String.class);

        return resp;
    }
}
