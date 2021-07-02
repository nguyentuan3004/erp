package com.samsung.erp.services;

import com.samsung.erp.entities.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleServiceTest {
    @Autowired
    RoleService roleService;

    @Test
    void getRole() {
        Role role = roleService.getRole(1L);

        System.out.println(role.getName());

        assertNotNull(role);
        assertEquals(role.getName(), "SUPERADMIN");
    }
}