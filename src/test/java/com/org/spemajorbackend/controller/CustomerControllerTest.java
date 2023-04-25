package com.org.spemajorbackend.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.spemajorbackend.dro.AddReviewRequest;
import com.org.spemajorbackend.dto.CustomerProfileResponse;
import com.org.spemajorbackend.service.CustomerService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

    /**
     * Method under test: {@link CustomerController#getMessList()}
     */
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

    /**
     * Method under test: {@link CustomerController#addReview(AddReviewRequest)}
     */
    @Test
    void testAddReview() throws Exception {
        when(customerService.addReview(Mockito.<AddReviewRequest>any())).thenReturn(true);

        AddReviewRequest addReviewRequest = new AddReviewRequest();
        addReviewRequest.setComment("Comment");
        addReviewRequest.setCustomer_username("janedoe");
        addReviewRequest.setMess_owner_username("janedoe");
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

    /**
     * Method under test: {@link CustomerController#getProfile(String)}
     */
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
}

