package com.org.spemajorbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthMaster {
    @Id
    private String username;
    private String password;
    private String role;
}
