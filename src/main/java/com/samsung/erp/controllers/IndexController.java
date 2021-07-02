package com.samsung.erp.controllers;

import com.samsung.erp.aspects.LogExecutionTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("")
public class IndexController {

    @Autowired
    Environment env;

    @Autowired
    private JavaMailSender emailSender;

    @GetMapping("")
    @ResponseBody
    public String getIndex() {
        env.getProperty("spring.mvc.view.prefix");
        System.out.println("Hello hahahaa");
        System.out.println("Tuantuna");
        System.out.println("hello shoppe");
        return "Hello Index";
    }

    @GetMapping("song")
    public String getPageSong(Model model) {
        model.addAttribute("Song", "If you are happy and you know it.");
        return "Song";
    }

    @PostMapping("test-email")
    @ResponseBody
    public void testEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vinh.lakien@stdio.vn");
        message.setTo("tam.la@stdio.vn");
        message.setSubject("Email send from Spring project ERP");
        message.setText("Content for email");
        emailSender.send(message);
    }

    private final Path root = Paths.get("uploads");

    @PostMapping("upload")
    public ResponseEntity<Void> uploadFile(@RequestParam("id") Long id,
                                            @RequestParam("file") MultipartFile file) {
        try {
            Files.createDirectories(root);
            Files.deleteIfExists(root.resolve(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        return ResponseEntity.ok().body(null);
    }
}
