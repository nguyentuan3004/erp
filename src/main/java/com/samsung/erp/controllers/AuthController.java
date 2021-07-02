package com.samsung.erp.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.samsung.erp.entities.Profile;
import com.samsung.erp.entities.Role;
import com.samsung.erp.entities.User;
import com.samsung.erp.security.Constants;
import com.samsung.erp.security.CustomUser;
import com.samsung.erp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("")
public class AuthController {
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestParam("email") String email,
                                        @RequestParam("password") String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String token = "";

        try {
            Algorithm algorithm = Algorithm.HMAC256(Constants.jwtKey);

            String[] roles = customUser.getAuthorities().stream()
                    .map(authority -> authority.getAuthority()).toArray(String[]::new);

            token = JWT.create()
                    .withClaim("id", customUser.getId())
                    .withClaim("username", customUser.getUsername())
                    .withArrayClaim("roles", roles)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 3600 * 1000))
                    .sign(algorithm);
        } catch (IllegalArgumentException e) {
        }

        if (token == "")
            return ResponseEntity.status(401).body(token);

        return ResponseEntity.ok().body(token);
    }

    @PostMapping("change-password")
    public ResponseEntity<Void> changePassword(@RequestParam("password") String password,
                                  @RequestParam("newPassword") String newPassword) {
        CustomUser customUser = (CustomUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changePassword(customUser.getId(), password, newPassword);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("request-recover-password")
    public ResponseEntity<Void> requestRecoverPassword(@RequestParam("email") String email) {
        userService.requestRecoverPassword(email);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("recover-password")
    public ResponseEntity<Void> recoverPassword(@RequestParam("newPassword") String newPassword,
                                                @RequestParam("passwordRecoverCode") String passwordRecoverCode) {
        userService.recoverPassword(passwordRecoverCode, newPassword);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestParam("firstName") String firstName,
                                         @RequestParam("lastName") String lastName,
                                         @RequestParam("email") String email,
                                         @RequestParam("password") String password,
                                         @RequestParam("referralId") Long referralId
    ) {
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

        User newUser = userService.register(user, profile);

        if (newUser != null)
            return ResponseEntity.ok().body(newUser);
        else
            return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("activate-user")
    public ResponseEntity<User> activateUser(@RequestParam("activatedCode") String activatedCode) {
        return ResponseEntity.ok().body(userService.activateUser(activatedCode));
    }
}
