package com.org.spemajorbackend.service;

import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.entity.JoiningRequest;
import com.org.spemajorbackend.entity.Menu;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.repository.CustomerRepository;
import com.org.spemajorbackend.repository.JoiningRequestRepository;
import com.org.spemajorbackend.repository.MenuRepository;
import com.org.spemajorbackend.repository.MessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final MessRepository messRepository;
    private final MenuRepository menuRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    private JoiningRequestRepository requestRepository;

    public CustomerService(MessRepository messRepository, MenuRepository menuRepository, CustomerRepository customerRepository) {
        this.messRepository = messRepository;
        this.menuRepository = menuRepository;
        this.customerRepository = customerRepository;
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

    public boolean sendJoinRequest(String customerId, String ownerId) {
        try{
            Customer customer = customerRepository.findById(customerId).get();
            Mess mess  = messRepository.findById(ownerId).orElseThrow(
                    () -> new UsernameNotFoundException("No mess exist owned by:"+ownerId)
            );
            requestRepository.save(new JoiningRequest(customer, mess));
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
