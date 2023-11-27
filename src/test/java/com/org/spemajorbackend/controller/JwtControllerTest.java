package com.org.spemajorbackend.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.spemajorbackend.dro.JwtRequest;
import com.org.spemajorbackend.dto.JwtResponse;
import com.org.spemajorbackend.entity.AuthMaster;
import com.org.spemajorbackend.repository.AuthMasterRepository;
import com.org.spemajorbackend.service.JwtService;

import java.util.Optional;

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

@ContextConfiguration(classes = {JwtController.class})
@ExtendWith(SpringExtension.class)
class JwtControllerTest {
    @MockBean
    private AuthMasterRepository authMasterRepository;
    @Autowired
    private JwtController jwtController;
    @MockBean
    private JwtService jwtService;

    @Test
    void testCreateJwtToken() throws Exception {
        AuthMaster authMaster = new AuthMaster();
        authMaster.setPassword("iloveyou");
        authMaster.setRole("Role");
        authMaster.setUsername("janedoe");
        Optional<AuthMaster> ofResult = Optional.of(authMaster);
        when(authMasterRepository.existsById(Mockito.<String>any())).thenReturn(true);
        when(authMasterRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(jwtService.createJwtToken(Mockito.<JwtRequest>any())).thenReturn(new JwtResponse("janedoe", "Role", "ABC123"));

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("iloveyou");
        jwtRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(jwtRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(jwtController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"username\":\"janedoe\",\"role\":\"Role\",\"token\":\"ABC123\"}"));
    }


    @Test
    void testCreateJwtToken2() throws Exception {
        AuthMaster authMaster = new AuthMaster();
        authMaster.setPassword("iloveyou");
        authMaster.setRole("Role");
        authMaster.setUsername("janedoe");
        Optional<AuthMaster> ofResult = Optional.of(authMaster);
        when(authMasterRepository.existsById(Mockito.<String>any())).thenReturn(true);
        when(authMasterRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(jwtService.createJwtToken(Mockito.<JwtRequest>any())).thenThrow(new Exception("?"));

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("iloveyou");
        jwtRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(jwtRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(jwtController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Invlid Credentials"));
    }


    @Test
    void testCreateJwtToken3() throws Exception {
        AuthMaster authMaster = new AuthMaster();
        authMaster.setPassword("iloveyou");
        authMaster.setRole("Role");
        authMaster.setUsername("janedoe");
        Optional<AuthMaster> ofResult = Optional.of(authMaster);
        when(authMasterRepository.existsById(Mockito.<String>any())).thenReturn(false);
        when(authMasterRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(jwtService.createJwtToken(Mockito.<JwtRequest>any())).thenReturn(new JwtResponse("janedoe", "Role", "ABC123"));

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setPassword("iloveyou");
        jwtRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(jwtRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(jwtController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Invlid Credentials"));
    }
}
