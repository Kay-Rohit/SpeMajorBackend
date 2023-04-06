package com.org.spemajorbackend.dro;

import lombok.Data;

@Data
public class AddReviewRequest {
    private Integer rating;
    private String comment;
    private String customer_username;
    private String mess_owner_username;
}
