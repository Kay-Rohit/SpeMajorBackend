package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.dro.JwtRequest;
import com.org.spemajorbackend.dto.JwtResponse;
import com.org.spemajorbackend.entity.AuthMaster;
import com.org.spemajorbackend.repository.AuthMasterRepository;
import com.org.spemajorbackend.service.JwtService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthMasterRepository authMasterRepository;

    @PostMapping("/authenticate")
    public JwtResponse createJwtToken(@NotNull @RequestBody JwtRequest jwtRequest) throws Exception {
//        System.out.println("Inside JWT controller");
        AuthMaster existingUser = authMasterRepository.findById(jwtRequest.getUsername()).get();
        System.out.println("Existing user in /authenticate request"+existingUser);

        if(authMasterRepository.existsById(jwtRequest.getUsername())) {
            try {
                JwtResponse response = jwtService.createJwtToken(jwtRequest);
                return response;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        //returning NA if user could not log in
        return new JwtResponse(
                "NA",
                "NA",
                "NA"
        );
    }
}
