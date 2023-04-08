package com.org.spemajorbackend.dro;

import lombok.Data;

@Data
public class UpdateMessDetails {

    private String phone;

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

    //pricing per month
    private String pricing;
}
