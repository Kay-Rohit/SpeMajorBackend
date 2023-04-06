package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
