package com.org.spemajorbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "joining_request",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"customer_username", "mess_owner_username"},
                name = "unique_connection_request"
        )
)
@Data
@NoArgsConstructor
public class JoiningRequest {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name="UUID", strategy = "uuid4")
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)",name="connection_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="customer_username")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="mess_owner_username")
    private Mess mess;

    public JoiningRequest(Customer customer, Mess mess) {
        this.customer = customer;
        this.mess = mess;
    }
}
