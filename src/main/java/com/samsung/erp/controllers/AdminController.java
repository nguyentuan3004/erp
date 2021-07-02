package com.samsung.erp.controllers;

import com.samsung.erp.entities.Profile;
import com.samsung.erp.entities.Role;
import com.samsung.erp.entities.User;
import com.samsung.erp.security.CustomUser;
import com.samsung.erp.services.ProfileService;
import com.samsung.erp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("admins")
public class AdminController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    @PostMapping("root")
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
        role.setId(2L);
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
    public ResponseEntity<List<Profile>> getAdmins() {
        return ResponseEntity.ok().body(profileService.findAll("ADMIN"));
    }

    @GetMapping("{id}")
    public ResponseEntity<Profile> getAdmin(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(profileService.findByIdAndRoleName(id, "ADMIN"));
    }

    @PostMapping("profile")
    public ResponseEntity<User> createCollaborator(@RequestParam("firstName") String firstName,
                                                   @RequestParam("lastName") String lastName,
                                                   @RequestParam("email") String email,
                                                   @RequestParam("password") String password,
                                                   @RequestParam("referralId") Long referralId) {
        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Profile admin = new Profile();
        admin.setId(customUser.getId());

        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setId(4L);
        roles.add(role);

        Profile referrer = new Profile();
        referrer.setId(referralId);

        Profile profile = new Profile();
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setRoles(roles);
        profile.setReferrer(referrer);

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        User newUser = userService.createUser(admin, user, profile);

        if (newUser != null)
            return ResponseEntity.ok().body(newUser);
        else
            return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
