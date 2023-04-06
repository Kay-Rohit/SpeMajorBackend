package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByMess_Username(String mess_owner_username);
}
