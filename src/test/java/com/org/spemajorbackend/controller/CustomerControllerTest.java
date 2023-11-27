package com.org.spemajorbackend.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.spemajorbackend.dro.AddReviewRequest;
import com.org.spemajorbackend.dro.AmountRequest;
import com.org.spemajorbackend.dro.ForgetPasswordRequest;
import com.org.spemajorbackend.dto.CustomerProfileResponse;
import com.org.spemajorbackend.service.CalculatorService;
import com.org.spemajorbackend.service.CustomerService;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private CalculatorService calculatorService;


    @Test
    void testGetMessList() throws Exception {
        when(customerService.getMessList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/get-all-mess");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetMessList2() throws Exception {
        when(customerService.getMessList()).thenReturn(new ArrayList<>());
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetMessList3() throws Exception {
        when(customerService.getMessList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/get-all-mess");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testSendJoinRequest() throws Exception {
        when(customerService.sendJoinRequest(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/join/{customer_id}/{owner_id}", "Customer id", "Owner id");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Request sent successfully!"));
    }

    @Test
    void testSendJoinRequest2() throws Exception {
        when(customerService.sendJoinRequest(Mockito.<String>any(), Mockito.<String>any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/join/{customer_id}/{owner_id}", "Customer id", "Owner id");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Request already sent"));
    }

    @Test
    void testSendJoinRequest3() throws Exception {
        when(customerService.sendJoinRequest(Mockito.<String>any(), Mockito.<String>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/join/{customer_id}/{owner_id}", "Customer id", "Owner id");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Request sent successfully!"));
    }

    @Test
    void testAddReview() throws Exception {
        when(customerService.addReview(Mockito.<AddReviewRequest>any())).thenReturn(true);

        AddReviewRequest addReviewRequest = new AddReviewRequest();
        addReviewRequest.setComment("Comment");
        addReviewRequest.setCustomer_username("janedoe");
        addReviewRequest.setMess_owner_username("janedoe2");
        addReviewRequest.setRating(1);
        String content = (new ObjectMapper()).writeValueAsString(addReviewRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/add-review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Review Added Successfully"));
    }

    @Test
    void testAddReview2() throws Exception {
        when(customerService.addReview(Mockito.<AddReviewRequest>any())).thenReturn(false);

        AddReviewRequest addReviewRequest = new AddReviewRequest();
        addReviewRequest.setComment("Comment");
        addReviewRequest.setCustomer_username("janedoe");
        addReviewRequest.setMess_owner_username("janedoe2");
        addReviewRequest.setRating(1);
        String content = (new ObjectMapper()).writeValueAsString(addReviewRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/add-review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Already added review!"));
    }


    @Test
    void testGetProfile() throws Exception {
        when(customerService.getProfile(Mockito.<String>any()))
                .thenReturn(new CustomerProfileResponse("janedoe", "Jane", "Doe", "jane.doe@example.org", "6625550144",
                        "janedoe", "Messname", "Jane", "Doe", "6625550144", "42 Main St", new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/get-profile/{customer_id}",
                "Customer id");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"username\":\"janedoe\",\"firstname\":\"Jane\",\"lastname\":\"Doe\",\"email\":\"jane.doe@example.org\",\"phone\":"
                                        + "\"6625550144\",\"ownerUsername\":\"janedoe\",\"messname\":\"Messname\",\"ownerFirstname\":\"Jane\",\"ownerLastname\""
                                        + ":\"Doe\",\"ownerPhone\":\"6625550144\",\"address\":\"42 Main St\",\"menus\":[]}"));
    }

    @Test
    void testResetPassword() throws Exception {
        when(customerService.resetPassword(Mockito.<ForgetPasswordRequest>any())).thenReturn(true);

        ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
        forgetPasswordRequest.setNewPassword("password");
        forgetPasswordRequest.setOldPassword("newpassword");
        forgetPasswordRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(forgetPasswordRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Password Reset was successfull"));
    }

    @Test
    void testResetPassword2() throws Exception {
        when(customerService.resetPassword(Mockito.<ForgetPasswordRequest>any())).thenReturn(false);

        ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
        forgetPasswordRequest.setNewPassword("password");
        forgetPasswordRequest.setOldPassword("password3");
        forgetPasswordRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(forgetPasswordRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Unable to change the password"));
    }


    @Test
    void testCalculateAmounnt() throws Exception {
        when(calculatorService.calculateAmount(Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Date>any(),
                Mockito.<String>any())).thenReturn(10.0f);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.get("/customer/calculate-amount")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new AmountRequest()));
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }

}
