package com.org.spemajorbackend.service;

import com.org.spemajorbackend.dro.AddReviewRequest;
import com.org.spemajorbackend.dro.ForgetPasswordRequest;
import com.org.spemajorbackend.dto.CustomerProfileResponse;
import com.org.spemajorbackend.entity.*;
import com.org.spemajorbackend.repository.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final MessRepository messRepository;
    private final MenuRepository menuRepository;
    private final CustomerRepository customerRepository;
    private final JoiningRequestRepository requestRepository;
    private final ReviewRepository reviewRepository;
    
    private final AuthMasterRepository authMasterRepository;

    public CustomerService(MessRepository messRepository, MenuRepository menuRepository, CustomerRepository customerRepository, JoiningRequestRepository requestRepository, ReviewRepository reviewRepository, AuthMasterRepository authMasterRepository) {
        this.messRepository = messRepository;
        this.menuRepository = menuRepository;
        this.customerRepository = customerRepository;
        this.requestRepository = requestRepository;
        this.reviewRepository = reviewRepository;
        this.authMasterRepository = authMasterRepository;
    }

    public List<Mess> getMessList() {

        List<Mess> messes = new ArrayList<Mess>();
//        List<Menu> allreviews = new ArrayList<Menu>();

        messRepository.findAll().forEach(
                mess -> messes.add(mess)
        );
        for (Mess mess : messes) {
            mess.setMenus(menuRepository.findByMess_Username(mess.getUsername()));
            mess.setReviews(reviewRepository.findByMess_Username(mess.getUsername()));
            mess.setCustomers(null);
        }
        return messes;

//        return messRepository.findAll();
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

    public boolean addReview(AddReviewRequest review) {
        Mess mess = messRepository.findById(review.getMess_owner_username()).orElseThrow(
                () -> new UsernameNotFoundException("No mess found with owner:"+review.getMess_owner_username())
        );
        Customer customer = customerRepository.findById(review.getCustomer_username()).get();
        try{

            List<Review> r = reviewRepository.findByCustomer_UsernameAndMess_Username(customer.getUsername(), mess.getUsername());
            if(r.isEmpty())
                reviewRepository.save(
                        new Review(
                                review.getRating(),
                                review.getComment(),
                                LocalDateTime.now(),
                                mess,
                                customer,
                                customer.getUsername()
                                )
                );
            else
                reviewRepository.updateReviewByMessAndCustomerUsernames(
                        review.getRating(),
                        review.getComment(),
                        LocalDateTime.now(),
                        customer.getUsername(),
                        mess.getUsername()
                );
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public CustomerProfileResponse getProfile(String customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new UsernameNotFoundException("No customer found with username:"+customerId)
        );
        System.out.println("customer fetched with username "+customer.getUsername());

        //check if there is mess assigned to a customer or not
//        System.out.println(customer.getMess());
        if(customer.getMess() != null){
            Mess assignedMess = messRepository.findById(customer.getMess().getUsername()).get();
            System.out.println("mess fetched"+ assignedMess.getMessname());

            List<Menu> menus = menuRepository.findByMess_Username(customer.getMess().getUsername());

            CustomerProfileResponse response =
                    new CustomerProfileResponse(
                            customer.getUsername(),
                            customer.getFirstname(),
                            customer.getLastname(),
                            customer.getEmail(),
                            customer.getPhone(),
                            assignedMess.getUsername(),
                            assignedMess.getMessname(),
                            assignedMess.getFirstname(),
                            assignedMess.getLastname(),
                            assignedMess.getPhone(),
                            assignedMess.getAddress(),
                            menus
                    );
            return response;
        }
        else{
            System.out.println("Inside else");
            CustomerProfileResponse response =
                    new CustomerProfileResponse(
                            customer.getUsername(),
                            customer.getFirstname(),
                            customer.getLastname(),
                            customer.getEmail(),
                            customer.getPhone(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    );
            return response;
        }
    }

    public boolean resetPassword(ForgetPasswordRequest forgetPasswordRequest) {
        String username = forgetPasswordRequest.getUsername();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AuthMaster authuser = authMasterRepository.findById(username).orElseThrow(
                () -> new UsernameNotFoundException("No customer found with username:"+username)
        );
//        checking if existing password matched with send old password
        if(authuser.getPassword()==passwordEncoder.encode(forgetPasswordRequest.getOldPassword())){
            authMasterRepository.save(
                    new AuthMaster(
                            authuser.getUsername(),
                            passwordEncoder.encode(forgetPasswordRequest.getNewPassword()),
                            authuser.getRole()
                    )
            );
        }
        else{
            return false;
        }
        return true;
    }
}
