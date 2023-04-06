package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.dro.AddReviewRequest;
import com.org.spemajorbackend.dto.ReviewResponse;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.service.CustomerService;
import com.sun.istack.NotNull;
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
    public ResponseEntity<?> sendJoinRequest(@NotNull @PathVariable String customer_id, @NotNull @PathVariable String owner_id){
        boolean accepted = customerService.sendJoinRequest(customer_id, owner_id);
        if(accepted)
            return ResponseEntity.ok("Request sent successfully!");
        return ResponseEntity.badRequest().body("Request already sent");
    }

    @PostMapping("/add-review")
    public ResponseEntity<?> addReview(@NotNull @RequestBody AddReviewRequest review){
        try{
            boolean added = customerService.addReview(review);
            if(added)
                return ResponseEntity.ok("Review Added Successfully");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseEntity.badRequest().body("Already added review!");
    }
}
