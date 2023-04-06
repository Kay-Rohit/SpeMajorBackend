package com.org.spemajorbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
