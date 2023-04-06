package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessRepository extends JpaRepository<Mess, String> {
}
