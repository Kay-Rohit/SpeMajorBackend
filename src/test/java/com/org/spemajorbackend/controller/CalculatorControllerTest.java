package com.org.spemajorbackend.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.spemajorbackend.dro.BMI;
import com.org.spemajorbackend.service.CalculatorService;
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

@ContextConfiguration(classes = {CalculatorController.class})
@ExtendWith(SpringExtension.class)
class CalculatorControllerTest {
    @Autowired
    private CalculatorController calculatorController;
    @MockBean
    private CalculatorService calculatorService;

    @Test
    void testCalculateBMI() throws Exception {
        when(calculatorService.calculateBMI(Mockito.<Double>any(), Mockito.<Double>any())).thenReturn(10.0d);

        BMI bmi = new BMI();
        bmi.setHeight(10.0d);
        bmi.setWeight(10.0d);
        String content = (new ObjectMapper()).writeValueAsString(bmi);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/calculate/bmi")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(calculatorController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }

    @Test
    void testCalculateBMI2() throws Exception {
        when(calculatorService.calculateBMI(Mockito.<Double>any(), Mockito.<Double>any())).thenReturn(10.0d);

        BMI bmi = new BMI();
        bmi.setHeight(0.0d);
        bmi.setWeight(10.0d);
        String content = (new ObjectMapper()).writeValueAsString(bmi);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/calculate/bmi")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(calculatorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Please pass valid height and weight"));
    }

    @Test
    void testCalculateBMI3() throws Exception {
        when(calculatorService.calculateBMI(Mockito.<Double>any(), Mockito.<Double>any())).thenReturn(10.0d);

        BMI bmi = new BMI();
        bmi.setHeight(10.0d);
        bmi.setWeight(-1.0d);
        String content = (new ObjectMapper()).writeValueAsString(bmi);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/calculate/bmi")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(calculatorController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Please pass valid height and weight"));
    }
}
