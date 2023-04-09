package com.org.spemajorbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mess")
@NoArgsConstructor
@Data
public class Mess {
    @Id
    private String username;
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

    //customers assigned to a mess
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name="assigned_owner_customer",
            joinColumns = @JoinColumn(name="mess_owner_username"),
            inverseJoinColumns = @JoinColumn(name="customer_username")
    )
    @JsonManagedReference
    private List<Customer> customers = new ArrayList<>();


    //mess weekly menu
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();




    public Mess(String username,
                String firstname,
                String lastname,
                String phone,
                String messname,
                String address,
                String latitude,
                String longitude,
                String service,
                String type,
                boolean trial,
                boolean breakfast,
                String aboutSundays,
                String pricing) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.messname = messname;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.service = service;
        this.type = type;
        this.trial = trial;
        this.breakfast = breakfast;
        this.aboutSundays = aboutSundays;
        this.pricing = pricing;
    }
}
