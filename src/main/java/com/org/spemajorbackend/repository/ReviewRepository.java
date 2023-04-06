package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Transactional
    @Modifying
    @Query("update Review r set r.rating = ?1, r.comment = ?2, r.createdAt = ?3 where r.customer.username = ?4 and r.mess.username = ?5")
    int updateReviewByMessAndCustomerUsernames(
            Integer rating,
            String comment,
            LocalDateTime createdAt,
            String customer_username,
            String mess_owner_username
    );

    List<Review> findByCustomer_UsernameAndMess_Username(String customer, String mess);

    List<Review> findByMess_Username(String mess_username);
}
