package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.dro.AddMenuRequest;
import com.org.spemajorbackend.service.MessService;
import com.sun.istack.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mess")
@PreAuthorize("hasRole('ROLE_OWNER')")
public class MessController {

    private final MessService messService;

    public MessController(MessService messService) {
        this.messService = messService;
    }

    @PostMapping("/add-menu/{mess_owner_username}")
    public ResponseEntity<?> addMenu(@NotNull @RequestBody List<AddMenuRequest> menuItems,@NotNull @PathVariable String mess_owner_username){
        boolean added = messService.addMenuItems(menuItems, mess_owner_username);
        if(added)
            return ResponseEntity.ok("Added Successfully");
        else
            return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping("/accept-request/{owner_id}/{customer_id}")
    public ResponseEntity<?> acceptRequest(@NotNull @PathVariable String owner_id, @NotNull @PathVariable String customer_id){
        ResponseEntity<?> status = messService.acceptRequest(owner_id, customer_id);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/owner-details/{owner_id}")
    public ResponseEntity<?> sendOwnerDetails(@NotNull @PathVariable String owner_id)
    {
        ResponseEntity<?> getDetails = messService.getOwnerDetails(owner_id);
        return ResponseEntity.ok(getDetails);
    }

}
