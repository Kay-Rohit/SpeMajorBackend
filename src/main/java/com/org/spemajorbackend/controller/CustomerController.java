package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get-all-mess")
    public ResponseEntity<?> getMessList(){
        List<Mess> messList = customerService.getMessList();
        return ResponseEntity.ok(messList);
    }

    @PostMapping("/join/{customer_id}/{owner_id}")
    public ResponseEntity<?> sendJoinRequest(@PathVariable String customer_id, @PathVariable String owner_id){
        boolean accepted = customerService.sendJoinRequest(customer_id, owner_id);
        if(accepted)
            return ResponseEntity.ok("Request sent successfully!");
        return ResponseEntity.badRequest().body("Request already sent");
    }
}
