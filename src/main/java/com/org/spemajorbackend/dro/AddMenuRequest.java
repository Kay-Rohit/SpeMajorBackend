package com.org.spemajorbackend.dro;

import lombok.Data;

@Data
public class AddMenuRequest {
    private String day;
    private String breakfast;
    private String lunch;
    private String dinner;
}
