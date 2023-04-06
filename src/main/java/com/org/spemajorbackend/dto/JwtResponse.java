package com.org.spemajorbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String username;
    private String role;
    private String token;
}
