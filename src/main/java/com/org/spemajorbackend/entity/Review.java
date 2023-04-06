package com.org.spemajorbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="review",
    uniqueConstraints = @UniqueConstraint(
            name = "unique_review",
            columnNames = {"mess_owner_username","customer_username"})
)
@Data
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name="UUID", strategy = "uuid4")
    @Type(type="org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)",name="connection_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "mess_owner_username")
    @JsonBackReference
    private Mess mess;

    @ManyToOne
    @JoinColumn(name = "customer_username")
    @JsonBackReference
    private Customer customer;

    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public Review(Integer rating, String comment, LocalDateTime createdAt, Mess mess, Customer customer) {
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.mess = mess;
        this.customer = customer;
    }
}
