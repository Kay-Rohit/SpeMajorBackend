package com.org.spemajorbackend.service;

import com.org.spemajorbackend.dro.AddMenuRequest;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.entity.JoiningRequest;
import com.org.spemajorbackend.entity.Menu;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.repository.CustomerRepository;
import com.org.spemajorbackend.repository.JoiningRequestRepository;
import com.org.spemajorbackend.repository.MenuRepository;
import com.org.spemajorbackend.repository.MessRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MessService {

    private final MessRepository messRepository;
    private final MenuRepository menuRepository;
    private final CustomerRepository customerRepository;
    private final JoiningRequestRepository requestRepository;

    public MessService(MessRepository messRepository, MenuRepository menuRepository, CustomerRepository customerRepository, JoiningRequestRepository requestRepository) {
        this.messRepository = messRepository;
        this.menuRepository = menuRepository;
        this.customerRepository = customerRepository;
        this.requestRepository = requestRepository;
    }

    public boolean addMenuItems(List<AddMenuRequest> menuItems, String mess_owner_username) {
        Mess mess = messRepository.findById(mess_owner_username).orElseThrow(
                () -> new UsernameNotFoundException("Cannot find the mess owned by"+mess_owner_username)
        );
        try{
            for (AddMenuRequest menuItem : menuItems) {
                Menu menu = new Menu(
                        menuItem.getDay(),
                        menuItem.getBreakfast(),
                        menuItem.getLunch(),
                        menuItem.getDinner()
                );
                menu.setMess(mess);
                menuRepository.save(menu);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Transactional
    public ResponseEntity<?> acceptRequest(String ownerId, String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new UsernameNotFoundException("Customer not found with username:"+customerId)
        );
        Mess mess = messRepository.findById(ownerId).get();

        try{
            List<Customer> customerList = mess.getCustomers();
            //delete the request
            List<JoiningRequest> byCustomerUserName = requestRepository.findByCustomer_Username(customer.getUsername());
            requestRepository.deleteAll(byCustomerUserName);

            customerList.add(customer);
            mess.setCustomers(customerList);
            customer.setMess(mess);

            messRepository.save(mess);
            customerRepository.save(customer);

            return ResponseEntity.ok("Request Accepted Successfully");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
