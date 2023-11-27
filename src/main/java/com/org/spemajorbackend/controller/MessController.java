package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.dro.AddMenuRequest;
import com.org.spemajorbackend.dro.UpdateMessDetails;
import com.org.spemajorbackend.dto.AmountBalancesResponse;
import com.org.spemajorbackend.dto.JoinRequestResponse;
import com.org.spemajorbackend.service.CalculatorService;
import com.org.spemajorbackend.service.MessService;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//for loggin
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@RestController
@RequestMapping("/mess")
@PreAuthorize("hasRole('ROLE_OWNER')")
@Slf4j
public class MessController {

    private final MessService messService;
    private final CalculatorService calculatorService;

    private static final Logger logger = LogManager.getLogger(MessController.class);

    public MessController(MessService messService, CalculatorService calculatorService) {
        this.messService = messService;
        this.calculatorService = calculatorService;
    }

    @PostMapping("/add-menu/{mess_owner_username}")
    public ResponseEntity<?> addMenu(@NotNull @RequestBody List<AddMenuRequest> menuItems,@NotNull @PathVariable String mess_owner_username){
        logger.info("[Add menu called by mess owner] -  " + mess_owner_username);
        boolean added = messService.addMenuItems(menuItems, mess_owner_username);
        if(added)
            return ResponseEntity.ok("Added Successfully");
        else
            return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping("/accept-request/{owner_id}/{customer_id}")
    public ResponseEntity<?> acceptRequest(@NotNull @PathVariable String owner_id, @NotNull @PathVariable String customer_id){
        logger.info("[Accept mess join request of customer] - " + customer_id);
        ResponseEntity<?> status = messService.acceptRequest(owner_id, customer_id);
        return status;
    }

    @PostMapping("/reject-request/{owner_id}/{customer_id}")
    public ResponseEntity<?> rejectRequest(@NotNull @PathVariable String owner_id,@NotNull @PathVariable String customer_id){
        log.info("[Rejected request of customer] - " + customer_id);
        ResponseEntity<?> status = messService.rejectRequest(owner_id, customer_id);
        return status;
    }
    @GetMapping("/owner-details/{owner_id}")
    public ResponseEntity<?> sendOwnerDetails(@NotNull @PathVariable String owner_id)
    {
        logger.info("[Get mess profile]");
        try {
            ResponseEntity<?> details = messService.getOwnerDetails(owner_id);
            if(details.equals(null))
                return ResponseEntity.unprocessableEntity().body("Error");
            return details;
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("updateOwner-details/{owner_id}")
    public ResponseEntity<?> updateOwnerDetails(@NotNull @PathVariable String owner_id, @NotNull @RequestBody UpdateMessDetails mess)
    {
        logger.info("[Update mess profile]");
        try {
            ResponseEntity<?> updateDetails = messService.updateOwnerDetails(owner_id, mess);
            return updateDetails;
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/requests/{owner_id}")
    public ResponseEntity<?> seeJoiningRequests(@NotNull @PathVariable String owner_id){
        logger.info("[Get all requests]");
        List<JoinRequestResponse> listOfRequests = messService.seeJoiningRequests(owner_id);
        return ResponseEntity.ok(listOfRequests);
    }

    @GetMapping("/total-balance/{owner_id}")
    public ResponseEntity<AmountBalancesResponse> amountBalance(@NotNull @PathVariable String owner_id){
        try{
            AmountBalancesResponse amountBalancesResponse = calculatorService.calculateTotal(owner_id);
            return ResponseEntity.ok().body(amountBalancesResponse);
        }
        catch (Exception e)
        {
            ResponseEntity.unprocessableEntity().body(e.getMessage());
        }
        return ResponseEntity.badRequest().body(new AmountBalancesResponse(0.0, 0.0, 0.0));
    }

}
