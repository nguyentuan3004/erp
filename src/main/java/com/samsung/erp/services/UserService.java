package com.samsung.erp.services;

import com.samsung.erp.entities.Profile;
import com.samsung.erp.entities.User;
import com.samsung.erp.repositories.ProfileRepository;
import com.samsung.erp.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    public User createUser(User user, Profile profile) {
        User pUser = userRepository.createUser(user);

        profile.setUser(pUser);
        profileRepository.createProfile(profile);

        return pUser;
    }

    public Boolean changePassword(Long id, String password, String newPassword) {
        User pUser = userRepository.findById(id);

        if (passwordEncoder.matches(password, pUser.getPassword())) {
            pUser.setPassword(passwordEncoder.encode(newPassword));
            return true;
        }

        return false;
    }

    public void requestRecoverPassword(String email) {
        User pUser = userRepository.findByEmail(email);

        if (pUser != null) {
            String recoverCode = pUser.getId().toString() + "-" + RandomStringUtils.random(8, true, true);
            pUser.setPasswordRecoverCode(recoverCode);

//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("ERP");
//            message.setTo("email");
//            message.setSubject("Recover password");
//            message.setText("https://test.com/recover-password?code=" + recoverCode);
//            emailSender.send(message);
        }
    }

    public void recoverPassword(String passwordRecoverCode, String newPassword) {
        String[] recoverCodes = passwordRecoverCode.split("-");
        Long id = Long.valueOf(recoverCodes[0]);

        User pUser = userRepository.findById(id);
        if (pUser != null) {
            if (pUser.getPasswordRecoverCode() != null &&
                    pUser.getPasswordRecoverCode().equals(passwordRecoverCode)) {
                pUser.setPassword(newPassword);
            }
        }
    }

    public User register(User user, Profile profile) {
        Profile pReferrer = profileRepository.findById(profile.getReferrer().getId());

        if (pReferrer == null)
            return null;

        Profile pAdmin = pReferrer.getAdmin();

        profile.setAdmin(pAdmin);

        user.setActivated(false);
        User pUser = userRepository.createUser(user);

        profile.setUser(pUser);
        profileRepository.createProfile(profile);

        String activatedCode = user.getId().toString() + "-" + RandomStringUtils.random(8, true, true);
        pUser.setActivatedCode(activatedCode);

        // SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("ERP");
//            message.setTo("email");
//            message.setSubject("Activate your account");
//            message.setText("https://test.com/activate-account?code=" + activatedCode);
//            emailSender.send(message);

        return pUser;
    }

    public User activateUser(String activatedCode) {
        String[] activatedCodes = activatedCode.split("-");
        Long id = Long.valueOf(activatedCodes[0]);

        User pUser = userRepository.findById(id);
        if (pUser != null) {
            if (pUser.getActivatedCode() != null &&
                    pUser.getActivatedCode().equals(activatedCode)) {
                pUser.setActivated(true);
                pUser.setActivatedCode(null);
            }
        }

        return pUser;
    }

    public User createUser(Profile admin, User user, Profile profile) {
        return null;
    }
}
