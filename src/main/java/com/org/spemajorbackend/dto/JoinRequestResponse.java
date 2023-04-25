package com.org.spemajorbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestResponse {
    private String mess_owner_username;
    private String customer_username;
}
