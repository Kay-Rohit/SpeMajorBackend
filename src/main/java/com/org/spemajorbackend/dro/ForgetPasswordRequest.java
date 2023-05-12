package com.org.spemajorbackend.dro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPasswordRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}
