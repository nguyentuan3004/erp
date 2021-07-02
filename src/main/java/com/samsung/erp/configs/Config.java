package com.samsung.erp.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

@Configuration
public class Config implements WebMvcConfigurer {
    @Bean
    public JavaMailSenderImpl getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("vinh.lakien@stdio.vn");
        mailSender.setPassword("apxfvauiuhjbnecg");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", "true");

        return mailSender;
    }

//    @Override
//    public void addResourceHandlers (ResourceHandlerRegistry registry) {
//        if (System.getProperty("os.name").contains("Windows")) {
//            registry.addResourceHandler("/assets/**").addResourceLocations("file:///" + uploadHelper.getPath("root") + "/assets/");
//        } else {
//            // registry.addResourceHandler("/statics/**").addResourceLocations("file:" + UploadHelper.getPath("ROOT") + "/");
//        }
//    }

}
