package com.samsung.erp.controllers;

import com.samsung.erp.entities.Profile;
import com.samsung.erp.entities.Role;
import com.samsung.erp.entities.User;
import com.samsung.erp.services.ProfileService;
import com.samsung.erp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("editors")
public class EditorController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<User> createAdmin(@RequestParam("firstName") String firstName,
                                                 @RequestParam("lastName") String lastName,
                                                 @RequestParam("email") String email,
                                                 @RequestParam("password") String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        Profile profile = new Profile();

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(3L);
        roles.add(role);

        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setRoles(roles);
        profile.setRoot(false);

        User newUser = userService.createUser(user, profile);

        if (newUser != null)
            return ResponseEntity.ok().body(newUser);
        else
            return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("")
    public ResponseEntity<List<Profile>> getEditors() {
        return ResponseEntity.ok().body(profileService.findAll("EDITOR"));
    }

    @GetMapping("{id}")
    public ResponseEntity<Profile> getEditor(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(profileService.findByIdAndRoleName(id, "EDITOR"));
    }
}
