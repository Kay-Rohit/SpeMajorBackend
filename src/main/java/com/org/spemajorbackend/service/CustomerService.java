package com.org.spemajorbackend.service;

import com.org.spemajorbackend.entity.Menu;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.repository.MenuRepository;
import com.org.spemajorbackend.repository.MessRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final MessRepository messRepository;
    private final MenuRepository menuRepository;

    public CustomerService(MessRepository messRepository, MenuRepository menuRepository) {
        this.messRepository = messRepository;
        this.menuRepository = menuRepository;
    }

    public List<Mess> getMessList() {

        List<Mess> messes = new ArrayList<Mess>();
        List<Menu> menuItems = new ArrayList<Menu>();

        messRepository.findAll().forEach(
                mess -> messes.add(mess)
        );
        for (Mess mess : messes) {
            mess.setMenus(menuRepository.findByMess_Username(mess.getUsername()));
            mess.setCustomers(null);
        }
        return messes;
    }
}
