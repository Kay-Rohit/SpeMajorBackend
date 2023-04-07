package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.dro.CustomerRegRequest;
import com.org.spemajorbackend.dro.MessRegRequest;
import com.org.spemajorbackend.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register-new-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegRequest request){
        System.out.println("Inside Registration Controller");
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
