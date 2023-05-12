package com.org.spemajorbackend.service;

import com.github.javafaker.Faker;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.repository.AuthMasterRepository;
import com.org.spemajorbackend.repository.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final CustomerRepository customerRepository;
    private final AuthMasterRepository authMasterRepository;

    public EmailService(JavaMailSender emailSender, CustomerRepository customerRepository, AuthMasterRepository authMasterRepository) {
        this.emailSender = emailSender;
        this.customerRepository = customerRepository;
        this.authMasterRepository = authMasterRepository;
    }

    public ResponseEntity<?> forgotPassword(String email) {
        Faker faker = new Faker();
        String newPassword = faker.internet().password();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        try{
            Customer customer = customerRepository.findByEmail(email);
            if(!Objects.isNull(customer)){
                createMail(customer.getEmail(), "Your new credentials", newPassword);
                authMasterRepository.updatePasswordByUsername(passwordEncoder.encode(newPassword), customer.getUsername());
                return ResponseEntity.ok("check your email for credentials");
            }
            else{
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return ResponseEntity.badRequest().body("Not able to process request");
    }

    public void createMail(String to, String subject , String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message,true);
        helper.setFrom("Smtp.Email.Sender.User@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p><b>Your Login details for WhatAMess" +
                " application</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password;
        message.setContent(htmlMsg,"text/html");
        emailSender.send(message);
    }
}
