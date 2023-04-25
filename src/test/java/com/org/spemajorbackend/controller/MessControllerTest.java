package com.org.spemajorbackend.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.spemajorbackend.dro.AddMenuRequest;
import com.org.spemajorbackend.service.MessService;

import java.util.ArrayList;
import java.util.List;

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

@ContextConfiguration(classes = {MessController.class})
@ExtendWith(SpringExtension.class)
class MessControllerTest {
    @Autowired
    private MessController messController;

    @MockBean
    private MessService messService;

    /**
     * Method under test: {@link MessController#addMenu(List, String)}
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
}

