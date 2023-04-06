package com.org.spemajorbackend.dro;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
