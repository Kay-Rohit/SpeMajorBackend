package com.org.spemajorbackend.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.spemajorbackend.dro.AddMenuRequest;
import com.org.spemajorbackend.dro.UpdateMessDetails;
import com.org.spemajorbackend.dto.AmountBalancesResponse;
import com.org.spemajorbackend.service.CalculatorService;
import com.org.spemajorbackend.service.MessService;

import java.util.ArrayList;
import java.util.List;

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

@ContextConfiguration(classes = {MessController.class})
@ExtendWith(SpringExtension.class)
class MessControllerTest {
    @MockBean
    private CalculatorService calculatorService;

    @Autowired
    private MessController messController;

    @MockBean
    private MessService messService;

    /**
     * Method under test: {@link MessController#rejectRequest(String, String)}
     */
    @Test
    void testRejectRequest() throws Exception {
        Mockito.<ResponseEntity<?>>when(messService.rejectRequest(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/mess/reject-request/{owner_id}/{customer_id}", "Owner id", "Customer id");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link MessController#rejectRequest(String, String)}
     */
    @Test
    void testRejectRequest2() throws Exception {
        Mockito.<ResponseEntity<?>>when(messService.rejectRequest(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testAcceptRequest() throws Exception {
        Mockito.<ResponseEntity<?>>when(messService.acceptRequest(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/mess/accept-request/{owner_id}/{customer_id}", "Owner id", "Customer id");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link MessController#acceptRequest(String, String)}
     */
    @Test
    void testAcceptRequest2() throws Exception {
        Mockito.<ResponseEntity<?>>when(messService.acceptRequest(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test:
     * {@link MessController#updateOwnerDetails(String, UpdateMessDetails)}
     */
    @Test
    void testUpdateOwnerDetails() throws Exception {
        Mockito
                .<ResponseEntity<?>>when(
                        messService.updateOwnerDetails(Mockito.<String>any(), Mockito.<UpdateMessDetails>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        UpdateMessDetails updateMessDetails = new UpdateMessDetails();
        updateMessDetails.setAddress("42 Main St");
        updateMessDetails.setBreakfast(true);
        updateMessDetails.setLatitude("Latitude");
        updateMessDetails.setLongitude("Longitude");
        updateMessDetails.setMessname("Messname");
        updateMessDetails.setPhone("6625550144");
        updateMessDetails.setPricing("Pricing");
        updateMessDetails.setService("Service");
        updateMessDetails.setTrial(true);
        updateMessDetails.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(updateMessDetails);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/mess/updateOwner-details/{owner_id}", "Owner id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test:  {@link MessController#seeJoiningRequests(String)}
     */
    @Test
    void testSeeJoiningRequests() throws Exception {
        when(messService.seeJoiningRequests(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mess/requests/{owner_id}", "Owner id");
        MockMvcBuilders.standaloneSetup(messController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test:  {@link MessController#seeJoiningRequests(String)}
     */
    @Test
    void testSeeJoiningRequests2() throws Exception {
        when(messService.seeJoiningRequests(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mess/requests/{owner_id}", "Owner id");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(messController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test:  {@link MessController#addMenu(List, String)}
     */
    @Test
    void testAddMenu() throws Exception {
        when(messService.addMenuItems(Mockito.<List<AddMenuRequest>>any(), Mockito.<String>any())).thenReturn(true);

        AddMenuRequest addMenuRequest = new AddMenuRequest();
        addMenuRequest.setBreakfast("Breakfast");
        addMenuRequest.setDay("Day");
        addMenuRequest.setDinner("Dinner");
        addMenuRequest.setLunch("Lunch");

        ArrayList<AddMenuRequest> addMenuRequestList = new ArrayList<>();
        addMenuRequestList.add(addMenuRequest);
        String content = (new ObjectMapper()).writeValueAsString(addMenuRequestList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/mess/add-menu/{mess_owner_username}", "janedoe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(messController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Added Successfully"));
    }

    /**
     * Method under test:  {@link MessController#addMenu(List, String)}
     */
    @Test
    void testAddMenu2() throws Exception {
        when(messService.addMenuItems(Mockito.<List<AddMenuRequest>>any(), Mockito.<String>any())).thenReturn(false);

        AddMenuRequest addMenuRequest = new AddMenuRequest();
        addMenuRequest.setBreakfast("Breakfast");
        addMenuRequest.setDay("Day");
        addMenuRequest.setDinner("Dinner");
        addMenuRequest.setLunch("Lunch");

        ArrayList<AddMenuRequest> addMenuRequestList = new ArrayList<>();
        addMenuRequestList.add(addMenuRequest);
        String content = (new ObjectMapper()).writeValueAsString(addMenuRequestList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/mess/add-menu/{mess_owner_username}", "janedoe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(422));
    }

    /**
     * Method under test:  {@link MessController#amountBalance(String)}
     */
    @Test
    void testAmountBalance() throws Exception {
        when(calculatorService.calculateTotal(Mockito.<String>any())).thenReturn(new AmountBalancesResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mess/total-balance/{owner_id}",
                "Owner id");
        MockMvcBuilders.standaloneSetup(messController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"paid\":null,\"remaining\":null,\"total\":null}"));
    }

    /**
     * Method under test: {@link MessController#sendOwnerDetails(String)}
     */
    @Test
    void testSendOwnerDetails() throws Exception {
        Mockito.<ResponseEntity<?>>when(messService.getOwnerDetails(Mockito.<String>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mess/owner-details/{owner_id}",
                "Owner id");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    /**
     * Method under test: {@link MessController#sendOwnerDetails(String)}
     */
    @Test
    void testSendOwnerDetails2() throws Exception {
        Mockito.<ResponseEntity<?>>when(messService.getOwnerDetails(Mockito.<String>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
