package com.org.spemajorbackend.controller;

import com.org.spemajorbackend.dro.AddMenuRequest;
import com.org.spemajorbackend.service.MenuService;
import com.sun.istack.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mess")
@PreAuthorize("hasRole('ROLE_OWNER')")
public class MessController {

    private final MenuService menuService;

    public MessController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/add-menu/{mess_owner_username}")
    public ResponseEntity<?> addMenu(@NotNull @RequestBody List<AddMenuRequest> menuItems, @PathVariable String mess_owner_username){
        boolean added = menuService.addMenuItems(menuItems, mess_owner_username);
        if(added)
            return ResponseEntity.ok("Added Successfully");
        else
            return ResponseEntity.unprocessableEntity().build();
    }
}
