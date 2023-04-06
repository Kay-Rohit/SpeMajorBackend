package com.org.spemajorbackend.service;

import com.org.spemajorbackend.dro.CustomerRegRequest;
import com.org.spemajorbackend.dro.MessRegRequest;
import com.org.spemajorbackend.entity.AuthMaster;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.repository.AuthMasterRepository;
import com.org.spemajorbackend.repository.CustomerRepository;
import com.org.spemajorbackend.repository.MessRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationService {
    private final PasswordEncoder passwordEncoder;
    private final AuthMasterRepository authRepository;
    private final CustomerRepository customerRepository;
    private final MessRepository messRepository;

    public RegistrationService(PasswordEncoder passwordEncoder, AuthMasterRepository authRepository, CustomerRepository customerRepository, MessRepository messRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authRepository = authRepository;
        this.customerRepository = customerRepository;
        this.messRepository = messRepository;
    }

    //to encode password
    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }

    //to roll back if something happens and the entire transaction cannot be completed.
    @Transactional
    public String registerCustomer(CustomerRegRequest request) {
        try{
            Boolean res = authRepository.findById(request.getUsername()).isEmpty();
            System.out.println(res);
            if( res ){

                authRepository.save(
                    new AuthMaster(
                            request.getUsername(),
                            getEncodedPassword( request.getPassword() ),
                            "CUSTOMER"
                    )
                );

                customerRepository.save(
                        new Customer(
                                request.getUsername(),
                                request.getFirstname(),
                                request.getLastname(),
                                request.getEmail(),
                                request.getPhone()
                        )
                );
                return "Registered Successfully";
            }
            else
                return "Username already taken!";

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "Some Error occured!!!";
    }


    @Transactional
    public String registerMess(MessRegRequest request) {
        try{
            Boolean res = authRepository.findById(request.getUsername()).isEmpty();
            System.out.println(res);
            if( res ){

                authRepository.save(
                        new AuthMaster(
                                request.getUsername(),
                                getEncodedPassword( request.getPassword() ),
                                "OWNER"
                        )
                );

                messRepository.save(
                        new Mess(
                                request.getUsername(),
                                request.getFirstname(),
                                request.getLastname(),
                                request.getPhone(),
                                request.getMessname(),
                                request.getAddress(),
                                request.getLatitude(),
                                request.getLongitude(),
                                request.getService(),
                                request.getType(),
                                request.isTrial(),
                                request.isBreakfast(),
                                request.getPricing()
                        )
                );
                return "Registered Successfully";
            }
            else
                return "Username already taken!";

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "Some Error occured!!!";
    }
}
