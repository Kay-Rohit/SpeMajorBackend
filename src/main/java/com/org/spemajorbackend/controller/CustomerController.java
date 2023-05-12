package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.dro.AddReviewRequest;
import com.org.spemajorbackend.dro.ForgetPasswordRequest;
import com.org.spemajorbackend.dto.CustomerProfileResponse;
import com.org.spemajorbackend.dto.ReviewResponse;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.service.CustomerService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//for logging
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get-all-mess")
    public ResponseEntity<?> getMessList(){
        logger.info("[Get all mess list]");
        List<Mess> messList = customerService.getMessList();
        return ResponseEntity.ok(messList);
    }

    @PostMapping("/join/{customer_id}/{owner_id}")
    public ResponseEntity<?> sendJoinRequest(@NotNull @PathVariable String customer_id, @NotNull @PathVariable String owner_id){
        logger.info("[Request to join a mess with id] - " + owner_id);
        boolean accepted = customerService.sendJoinRequest(customer_id, owner_id);
        if(accepted)
            return ResponseEntity.ok("Request sent successfully!");
        return ResponseEntity.accepted().body("Request already sent");
    }

    @PostMapping("/add-review")
    public ResponseEntity<?> addReview(@NotNull @RequestBody AddReviewRequest review){
        logger.info("[Add review for the mess] - " + review.getMess_owner_username() + "[by the customer] -" + review.getCustomer_username());
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

    @GetMapping("/get-profile/{customer_id}")
    public ResponseEntity<?> getProfile(@NotNull @PathVariable String customer_id){
        logger.info("[Get profile info of customer] - " + customer_id);
        try{
            CustomerProfileResponse customerProfile = customerService.getProfile(customer_id);
            return ResponseEntity.ok(customerProfile);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
//        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest){
        boolean status = customerService.resetPassword(forgetPasswordRequest);
        if(status)
            return ResponseEntity.ok("Password Reset was successfull");
        else
            return ResponseEntity.badRequest().body("Unable to change the password");
    }
}
