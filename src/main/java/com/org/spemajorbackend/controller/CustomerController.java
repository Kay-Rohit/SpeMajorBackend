package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
