package com.org.spemajorbackend;

import com.org.spemajorbackend.controller.CustomerController;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;

@SpringBootTest
class SpeMajorBackendApplicationTests {

    @Test
    public void testCustomerAPIs() throws Exception{

    }

}
