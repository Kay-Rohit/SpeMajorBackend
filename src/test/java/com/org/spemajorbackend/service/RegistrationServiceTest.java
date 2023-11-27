package com.org.spemajorbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.org.spemajorbackend.dro.CustomerRegRequest;
import com.org.spemajorbackend.dro.MessRegRequest;
import com.org.spemajorbackend.entity.AuthMaster;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.repository.AuthMasterRepository;
import com.org.spemajorbackend.repository.CustomerRepository;
import com.org.spemajorbackend.repository.MessRepository;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RegistrationService.class})
@ExtendWith(SpringExtension.class)
class RegistrationServiceTest {
    @MockBean
    private AuthMasterRepository authMasterRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private MessRepository messRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RegistrationService registrationService;


    @Test
    void testGetEncodedPassword() {
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        String actualEncodedPassword = registrationService.getEncodedPassword("iloveyou");
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals("secret", actualEncodedPassword);
    }


    @Test
    void testRegisterCustomer() {
        AuthMaster authMaster = new AuthMaster();
        authMaster.setPassword("iloveyou");
        authMaster.setRole("Role");
        authMaster.setUsername("janedoe");
        Optional<AuthMaster> ofResult = Optional.of(authMaster);
        when(authMasterRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        CustomerRegRequest request = new CustomerRegRequest();
        request.setEmail("jane.doe@example.org");
        request.setFirstname("Jane");
        request.setLastname("Doe");
        request.setPassword("iloveyou");
        request.setPhone("6625550144");
        request.setUsername("janedoe");
        String actualRegisterCustomerResult = registrationService.registerCustomer(request);
        verify(authMasterRepository).findById(Mockito.<String>any());
        assertEquals("Username already taken!", actualRegisterCustomerResult);
    }


    @Test
    void testRegisterCustomer2() {
        AuthMaster authMaster = new AuthMaster();
        authMaster.setPassword("iloveyou");
        authMaster.setRole("Role");
        authMaster.setUsername("janedoe");
        when(authMasterRepository.save(Mockito.<AuthMaster>any())).thenReturn(authMaster);
        Optional<AuthMaster> emptyResult = Optional.empty();
        when(authMasterRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        Mess mess = new Mess();
        mess.setAboutSundays("About Sundays");
        mess.setAddress("42 Main St");
        mess.setBreakfast(true);
        mess.setCustomers(new ArrayList<>());
        mess.setFirstname("Jane");
        mess.setLastname("Doe");
        mess.setLatitude("Latitude");
        mess.setLongitude("Longitude");
        mess.setMenus(new ArrayList<>());
        mess.setMessname("Messname");
        mess.setPhone("6625550144");
        mess.setPricing("Pricing");
        mess.setReviews(new ArrayList<>());
        mess.setService("Service");
        mess.setTrial(true);
        mess.setType("Type");
        mess.setUsername("janedoe");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setLastname("Doe");
        customer.setMess(mess);
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        CustomerRegRequest request = new CustomerRegRequest();
        request.setEmail("jane.doe@example.org");
        request.setFirstname("Jane");
        request.setLastname("Doe");
        request.setPassword("iloveyou");
        request.setPhone("6625550144");
        request.setUsername("janedoe");
        String actualRegisterCustomerResult = registrationService.registerCustomer(request);
        verify(authMasterRepository).findById(Mockito.<String>any());
        verify(authMasterRepository).save(Mockito.<AuthMaster>any());
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals("Registered Successfully", actualRegisterCustomerResult);
    }


    @Test
    void testRegisterMess() {
        AuthMaster authMaster = new AuthMaster();
        authMaster.setPassword("iloveyou");
        authMaster.setRole("Role");
        authMaster.setUsername("janedoe");
        Optional<AuthMaster> ofResult = Optional.of(authMaster);
        when(authMasterRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        MessRegRequest request = new MessRegRequest();
        request.setAboutSundays("About Sundays");
        request.setAddress("42 Main St");
        request.setBreakfast(true);
        request.setFirstname("Jane");
        request.setLastname("Doe");
        request.setLatitude("Latitude");
        request.setLongitude("Longitude");
        request.setMessname("Messname");
        request.setPassword("iloveyou");
        request.setPhone("6625550144");
        request.setPricing("Pricing");
        request.setService("Service");
        request.setTrial(true);
        request.setType("Type");
        request.setUsername("janedoe");
        String actualRegisterMessResult = registrationService.registerMess(request);
        verify(authMasterRepository).findById(Mockito.<String>any());
        assertEquals("Username already taken!", actualRegisterMessResult);
    }


    @Test
    void testRegisterMess2() {
        AuthMaster authMaster = new AuthMaster();
        authMaster.setPassword("iloveyou");
        authMaster.setRole("Role");
        authMaster.setUsername("janedoe");
        when(authMasterRepository.save(Mockito.<AuthMaster>any())).thenReturn(authMaster);
        Optional<AuthMaster> emptyResult = Optional.empty();
        when(authMasterRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        Mess mess = new Mess();
        mess.setAboutSundays("About Sundays");
        mess.setAddress("42 Main St");
        mess.setBreakfast(true);
        mess.setCustomers(new ArrayList<>());
        mess.setFirstname("Jane");
        mess.setLastname("Doe");
        mess.setLatitude("Latitude");
        mess.setLongitude("Longitude");
        mess.setMenus(new ArrayList<>());
        mess.setMessname("Messname");
        mess.setPhone("6625550144");
        mess.setPricing("Pricing");
        mess.setReviews(new ArrayList<>());
        mess.setService("Service");
        mess.setTrial(true);
        mess.setType("Type");
        mess.setUsername("janedoe");
        when(messRepository.save(Mockito.<Mess>any())).thenReturn(mess);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        MessRegRequest request = new MessRegRequest();
        request.setAboutSundays("About Sundays");
        request.setAddress("42 Main St");
        request.setBreakfast(true);
        request.setFirstname("Jane");
        request.setLastname("Doe");
        request.setLatitude("Latitude");
        request.setLongitude("Longitude");
        request.setMessname("Messname");
        request.setPassword("iloveyou");
        request.setPhone("6625550144");
        request.setPricing("Pricing");
        request.setService("Service");
        request.setTrial(true);
        request.setType("Type");
        request.setUsername("janedoe");
        String actualRegisterMessResult = registrationService.registerMess(request);
        verify(authMasterRepository).findById(Mockito.<String>any());
        verify(authMasterRepository).save(Mockito.<AuthMaster>any());
        verify(messRepository).save(Mockito.<Mess>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals("Registered Successfully", actualRegisterMessResult);
    }
}
