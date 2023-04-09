package com.org.spemajorbackend.repository;

import com.org.spemajorbackend.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByMess_Username(String mess_owner_username);
    Menu findByMess_UsernameAndAndDay(String mess_owner_username, String day);

    @Transactional
    @Modifying
    @Query("update Menu m set m.breakfast=?1, m.lunch=?2, m.dinner=?3 where m.day=?4 and m.mess.username=?5")
    int updateMenuByDay(String breakfast, String lunch, String dinner, String day, String mess_owner_username);
}
