package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.dro.JwtRequest;
import com.org.spemajorbackend.dto.JwtResponse;
import com.org.spemajorbackend.entity.AuthMaster;
import com.org.spemajorbackend.repository.AuthMasterRepository;
import com.org.spemajorbackend.service.JwtService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class JwtController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthMasterRepository authMasterRepository;

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    @PostMapping("/authenticate")
    public ResponseEntity<?> createJwtToken(@NotNull @RequestBody JwtRequest jwtRequest) throws Exception {
        AuthMaster existingUser = authMasterRepository.findById(jwtRequest.getUsername()).get();
//        System.out.println("Existing user in /authenticate request " + existingUser);
        logger.info("Existing user in /authenticate request " + existingUser);

        if(authMasterRepository.existsById(jwtRequest.getUsername())) {
            try {
                JwtResponse response = jwtService.createJwtToken(jwtRequest);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
//                System.out.println(e.getMessage());
                logger.info(e.getMessage());
            }
        }

        //returning NA if user could not log in
        return ResponseEntity.badRequest().body("Invlid Credentials");
    }
}
