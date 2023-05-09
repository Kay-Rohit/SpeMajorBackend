package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.dro.CustomerRegRequest;
import com.org.spemajorbackend.dro.MessRegRequest;
import com.org.spemajorbackend.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//for logging
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Controller
@Slf4j
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;
    private static final Logger logger = LogManager.getLogger(RegistrationController.class);

    @PostMapping("/register-new-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegRequest request){
        logger.info("[Register new customer with customer username as] - " + request.getUsername());
        log.info("[Register new customer with customer username] - " + request.getUsername());
        String response = registrationService.registerCustomer(request);

        if(response=="Username already taken!"){
            return ResponseEntity.accepted().body(response);
        }
        else if(response=="Registered Successfully")
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register-new-mess")
    public ResponseEntity<?> registerMess(@RequestBody MessRegRequest request){
        logger.info("[Register new customer with mess owner username as] - " + request.getUsername());
        String response = registrationService.registerMess(request);
        if(response=="Username already taken!"){
            return ResponseEntity.accepted().body(response);
        }
        else if(response=="Registered Successfully")
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().build();
    }
}
