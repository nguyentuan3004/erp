package com.samsung.erp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    void changePassword() {
        Boolean result = userService.changePassword(1L, "123456", "1234567");

        assertTrue(result);
    }

    @Test
    void requestRecoverPassword() {
        userService.requestRecoverPassword("vutrongquang@stdio.vn");
    }
}