package com.org.spemajorbackend.dro;

import lombok.Data;

@Data
public class MessRegRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;

    //mess details
    private String messname;
    private String address;
    private String latitude;
    private String longitude;
    //dine-in or deliverable
    private String service;
    //veg-nonveg
    private String type;
    private boolean trial;
    //if they provide breakfast as well
    private boolean breakfast;
    private String aboutSundays;
    //pricing per month
    private String pricing;
}
