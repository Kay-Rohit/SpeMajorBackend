package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
