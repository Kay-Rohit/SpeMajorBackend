package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.AuthMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthMasterRepository extends JpaRepository<AuthMaster, String> {
}
