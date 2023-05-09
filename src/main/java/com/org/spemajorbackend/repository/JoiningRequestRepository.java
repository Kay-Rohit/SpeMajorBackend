package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.entity.JoiningRequest;
import com.org.spemajorbackend.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JoiningRequestRepository extends JpaRepository<JoiningRequest, Long> {
    List<JoiningRequest> findByCustomer_Username(String customer_username);
    List<JoiningRequest> findByMess_Username(String mess_owner_username);

    @Query("delete from JoiningRequest d where d.customer = ?1 and d.mess = ?2")
    void deleteByCustomer_UsernameAndMess_Username(Customer customer, Mess mess);
}
