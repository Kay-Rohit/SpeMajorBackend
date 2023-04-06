package com.org.spemajorbackend.dro;

import lombok.Data;

@Data
public class CustomerRegRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
