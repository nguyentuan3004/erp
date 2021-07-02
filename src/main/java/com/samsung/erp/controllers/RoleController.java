package com.samsung.erp.controllers;

import com.samsung.erp.entities.Role;
import com.samsung.erp.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("")
    public Role createRole(@RequestParam("name") String name) {
        Role role = new Role();
        role.setName(name);
        return roleService.createRole(role);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable("id") Long id,
                           @RequestBody Role role) {
        role.setId(id);
        return roleService.updateRole(role);
    }

    @PutMapping("/{id}/archived")
    public Role archiveRole(@PathVariable("id") Long id,
                           @RequestBody Role role) {
        role.setId(id);
        return roleService.updateRole(role);
    }

    @GetMapping("")
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    @GetMapping("/{id}")
    public Role updateRole(@PathVariable("id") Long id) {
        return roleService.getRole(id);
    }
}
