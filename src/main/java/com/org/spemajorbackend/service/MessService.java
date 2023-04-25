package com.org.spemajorbackend.service;

import com.org.spemajorbackend.dro.AddMenuRequest;
import com.org.spemajorbackend.dro.UpdateMessDetails;
import com.org.spemajorbackend.dto.JoinRequestResponse;
import com.org.spemajorbackend.entity.*;
import com.org.spemajorbackend.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessService {

    private final MessRepository messRepository;
    private final MenuRepository menuRepository;
    private final CustomerRepository customerRepository;
    private final JoiningRequestRepository requestRepository;

    private final ReviewRepository reviewRepository;

    public MessService(MessRepository messRepository, MenuRepository menuRepository, CustomerRepository customerRepository, JoiningRequestRepository requestRepository, ReviewRepository reviewRepository) {
        this.messRepository = messRepository;
        this.menuRepository = menuRepository;
        this.customerRepository = customerRepository;
        this.requestRepository = requestRepository;
        this.reviewRepository = reviewRepository;
    }

    public boolean addMenuItems(List<AddMenuRequest> menuItems, String mess_owner_username) {
        Mess mess = messRepository.findById(mess_owner_username).orElseThrow(
                () -> new UsernameNotFoundException("Cannot find the mess owned by"+mess_owner_username)
        );
        try{
            for (AddMenuRequest menuItem : menuItems) {

                Menu existingDayMenu = menuRepository.findByMess_UsernameAndAndDay(mess_owner_username, menuItem.getDay());
//                save menu only if there is no menu item on that day available
                if(existingDayMenu == null){
                    Menu menu = new Menu(
                            menuItem.getDay(),
                            menuItem.getBreakfast(),
                            menuItem.getLunch(),
                            menuItem.getDinner()
                    );
                    menu.setMess(mess);
                    menuRepository.save(menu);
                }
                //else update
                else{
//                    updating menu is day already exist as we are storing only the weekly menu
                    menuRepository.updateMenuByDay(
                            menuItem.getBreakfast(),
                            menuItem.getLunch(),
                            menuItem.getDinner(),
                            menuItem.getDay(),
                            mess_owner_username
                    );
                }
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

    public ResponseEntity<?> getOwnerDetails(String owner_id)
    {
//        Optional<Mess> owner = messRepository.findById(owner_id);
        Mess owner = messRepository.findById(owner_id).orElseThrow(
                () -> new UsernameNotFoundException("Sorry no mess found owned by:"+owner_id)
        );
        try{

            List<Menu> menus = menuRepository.findByMess_Username(owner_id);
            List<Customer> customers = customerRepository.findByMess_Username(owner_id);
            List<Review> reviews = reviewRepository.findByMess_Username(owner_id);

            owner.setMenus(menus);
            owner.setCustomers(customers);
            owner.setReviews(reviews);
        }
        catch(Exception e){
            throw new RuntimeException("Employee is not found for the id"+owner_id);
        }
        return ResponseEntity.ok(owner);

    }


    public ResponseEntity<?> updateOwnerDetails(String owner_id, UpdateMessDetails mess) {
        Mess owner = messRepository.findById(owner_id).orElseThrow(
                () -> new UsernameNotFoundException("Sorry no mess found owned by:"+owner_id)
        );
        try{
            owner.setPhone(mess.getPhone());
            owner.setMessname(mess.getMessname());
            owner.setAddress(mess.getAddress());
            owner.setLatitude(mess.getLatitude());
            owner.setLongitude(mess.getLongitude());
            owner.setService(mess.getService());
            owner.setType(mess.getType());
            owner.setTrial(mess.isTrial());
            owner.setBreakfast(mess.isBreakfast());
            owner.setPricing(mess.getPricing());
            messRepository.save(owner);

        }
        catch(Exception e){
            throw new RuntimeException("Employee is not found for the id"+owner_id);
        }
        return ResponseEntity.ok(owner);
    }

    public List<JoinRequestResponse> seeJoiningRequests(String ownerId) {
        List<JoiningRequest> requests = requestRepository.findByMess_Username(ownerId);
        List<JoinRequestResponse> responses = new ArrayList<>();
        for (JoiningRequest request : requests) {
            responses.add(new JoinRequestResponse(
                    request.getMess().getUsername(),
                    request.getCustomer().getUsername()
            ));
        }

        return responses;
    }
}
