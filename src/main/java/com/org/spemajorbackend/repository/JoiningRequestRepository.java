package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.JoiningRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoiningRequestRepository extends JpaRepository<JoiningRequest, Long> {
    List<JoiningRequest> findByCustomer_Username(String customer_username);
}
