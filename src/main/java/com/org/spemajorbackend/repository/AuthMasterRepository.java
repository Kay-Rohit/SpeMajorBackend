package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.AuthMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AuthMasterRepository extends JpaRepository<AuthMaster, String> {
    @Transactional
    @Modifying
    @Query("update AuthMaster a set a.password = ?1 where a.username = ?2")
    int updatePasswordByUsername(String password, String username);
//    in the above quey, it returns numbers of rows effected
}
