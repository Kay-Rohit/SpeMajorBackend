package com.org.spemajorbackend.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.spemajorbackend.dro.CustomerRegRequest;
import com.org.spemajorbackend.dro.MessRegRequest;
import com.org.spemajorbackend.service.EmailService;
import com.org.spemajorbackend.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RegistrationController.class})
@ExtendWith(SpringExtension.class)
class RegistrationControllerTest {
    @MockBean
    private EmailService emailService;
    @Autowired
    private RegistrationController registrationController;
    @MockBean
    private RegistrationService registrationService;


    @Test
    void testRegisterCustomer() throws Exception {
        when(registrationService.registerCustomer(Mockito.<CustomerRegRequest>any())).thenReturn("Register Customer");

        CustomerRegRequest customerRegRequest = new CustomerRegRequest();
        customerRegRequest.setEmail("jane.doe@example.org");
        customerRegRequest.setFirstname("Jane");
        customerRegRequest.setLastname("Doe");
        customerRegRequest.setPassword("password");
        customerRegRequest.setPhone("6625550144");
        customerRegRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(customerRegRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register-new-customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(registrationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testRegisterCustomer2() throws Exception {
        when(registrationService.registerCustomer(Mockito.<CustomerRegRequest>any())).thenReturn("Username already taken!");

        CustomerRegRequest customerRegRequest = new CustomerRegRequest();
        customerRegRequest.setEmail("jane.doe@example.org");
        customerRegRequest.setFirstname("Jane");
        customerRegRequest.setLastname("Doe");
        customerRegRequest.setPassword("password");
        customerRegRequest.setPhone("6625550144");
        customerRegRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(customerRegRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register-new-customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(registrationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Username already taken!"));
    }

    @Test
    void testRegisterCustomer3() throws Exception {
        when(registrationService.registerCustomer(Mockito.<CustomerRegRequest>any())).thenReturn("Registered Successfully");

        CustomerRegRequest customerRegRequest = new CustomerRegRequest();
        customerRegRequest.setEmail("jane.doe@example.org");
        customerRegRequest.setFirstname("Jane");
        customerRegRequest.setLastname("Doe");
        customerRegRequest.setPassword("iloveyou");
        customerRegRequest.setPhone("6625550144");
        customerRegRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(customerRegRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register-new-customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(registrationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Registered Successfully"));
    }


    @Test
    void testRegisterMess() throws Exception {
        when(registrationService.registerMess(Mockito.<MessRegRequest>any())).thenReturn("Register Mess");

        MessRegRequest messRegRequest = new MessRegRequest();
        messRegRequest.setAboutSundays("About Sundays");
        messRegRequest.setAddress("42 Main St");
        messRegRequest.setBreakfast(true);
        messRegRequest.setFirstname("Jane");
        messRegRequest.setLastname("Doe");
        messRegRequest.setLatitude("Latitude");
        messRegRequest.setLongitude("Longitude");
        messRegRequest.setMessname("Messname");
        messRegRequest.setPassword("iloveyou");
        messRegRequest.setPhone("6625550144");
        messRegRequest.setPricing("Pricing");
        messRegRequest.setService("Service");
        messRegRequest.setTrial(true);
        messRegRequest.setType("Type");
        messRegRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(messRegRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register-new-mess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(registrationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testRegisterMess2() throws Exception {
        when(registrationService.registerMess(Mockito.<MessRegRequest>any())).thenReturn("Username already taken!");

        MessRegRequest messRegRequest = new MessRegRequest();
        messRegRequest.setAboutSundays("About Sundays");
        messRegRequest.setAddress("42 Main St");
        messRegRequest.setBreakfast(true);
        messRegRequest.setFirstname("Jane");
        messRegRequest.setLastname("Doe");
        messRegRequest.setLatitude("Latitude");
        messRegRequest.setLongitude("Longitude");
        messRegRequest.setMessname("Messname");
        messRegRequest.setPassword("iloveyou");
        messRegRequest.setPhone("6625550144");
        messRegRequest.setPricing("Pricing");
        messRegRequest.setService("Service");
        messRegRequest.setTrial(true);
        messRegRequest.setType("Type");
        messRegRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(messRegRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register-new-mess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(registrationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Username already taken!"));
    }


    @Test
    void testRegisterMess3() throws Exception {
        when(registrationService.registerMess(Mockito.<MessRegRequest>any())).thenReturn("Registered Successfully");

        MessRegRequest messRegRequest = new MessRegRequest();
        messRegRequest.setAboutSundays("About Sundays");
        messRegRequest.setAddress("42 Main St");
        messRegRequest.setBreakfast(true);
        messRegRequest.setFirstname("Jane");
        messRegRequest.setLastname("Doe");
        messRegRequest.setLatitude("Latitude");
        messRegRequest.setLongitude("Longitude");
        messRegRequest.setMessname("Messname");
        messRegRequest.setPassword("iloveyou");
        messRegRequest.setPhone("6625550144");
        messRegRequest.setPricing("Pricing");
        messRegRequest.setService("Service");
        messRegRequest.setTrial(true);
        messRegRequest.setType("Type");
        messRegRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(messRegRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register-new-mess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(registrationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Registered Successfully"));
    }

    @Test
    void testForgotPassword() throws Exception {
        Mockito.<ResponseEntity<?>>when(emailService.forgotPassword(Mockito.<String>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/forgot-password/{email}",
                "jane.doe@example.org");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(registrationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }


    @Test
    void testForgotPassword2() throws Exception {
        Mockito.<ResponseEntity<?>>when(emailService.forgotPassword(Mockito.<String>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(registrationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
