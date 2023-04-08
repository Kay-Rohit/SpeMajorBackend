package com.org.spemajorbackend.dto;

import com.org.spemajorbackend.entity.Menu;
import com.org.spemajorbackend.entity.Mess;
import lombok.Data;

import java.util.List;

@Data
public class CustomerProfileResponse {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    //mess
    private String ownerUsername;
    private String messname;

    private String ownerFirstname;
    private String ownerLastname;
    private String ownerPhone;
    private String address;
    private List<Menu> menus;

    public CustomerProfileResponse(String username,
                                   String firstname,
                                   String lastname,
                                   String email,
                                   String phone,
                                   String ownerUsername,
                                   String messname,
                                   String ownerFirstname,
                                   String ownerLastname,
                                   String ownerPhone,
                                   String address,
                                   List<Menu> menus) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.ownerUsername = ownerUsername;
        this.messname = messname;
        this.ownerFirstname = ownerFirstname;
        this.ownerLastname = ownerLastname;
        this.ownerPhone = ownerPhone;
        this.address = address;
        this.menus = menus;
    }
}
