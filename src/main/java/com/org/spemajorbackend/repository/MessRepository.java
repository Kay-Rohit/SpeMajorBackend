package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MessRepository extends JpaRepository<Mess, String> {

}
