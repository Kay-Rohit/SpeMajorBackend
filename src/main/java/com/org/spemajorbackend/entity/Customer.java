package com.org.spemajorbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="customer")
@NoArgsConstructor
@Data
public class Customer {
    @Id
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Boolean paid;

    @ManyToOne
    @JoinColumn(name = "mess_owner_username")
    @JsonIgnore
    private Mess mess;

    //dont add constructors for other objects and mapping entities
    public Customer(String username, String firstname, String lastname, String email, String phone, Boolean paid) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.paid = paid;
    }
}
