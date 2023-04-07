package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findByMess_Username(String mess_owner_username);
}
