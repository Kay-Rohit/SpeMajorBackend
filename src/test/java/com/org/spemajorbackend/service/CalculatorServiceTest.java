package com.org.spemajorbackend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.org.spemajorbackend.dro.AmountRequest;
import com.org.spemajorbackend.dto.AmountBalancesResponse;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.repository.CustomerRepository;
import com.org.spemajorbackend.repository.MessRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CalculatorService.class})
@ExtendWith(SpringExtension.class)
class CalculatorServiceTest {
    @Autowired
    private CalculatorService calculatorService;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private MessRepository messRepository;

    @Test
    void testCalculateAmount2() {
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
        Optional<Mess> ofResult = Optional.of(mess);
        when(messRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        when(customerRepository.findById(Mockito.<String>any())).thenThrow(new IllegalArgumentException("foo"));
        Date startDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        assertThrows(IllegalArgumentException.class, () -> calculatorService.calculateAmount("42", startDate,
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Customer ID"));
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
    }

    @Test
    void testCalculateAmount(){
        Date startdate = new Date(2023, 11, 1);
        Date endDate = new Date(2023, 11, 4);

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
        mess.setPricing("2000");
        mess.setReviews(new ArrayList<>());
        mess.setService("Service");
        mess.setTrial(true);
        mess.setType("Type");
        mess.setUsername("janedoe");
        Optional<Mess> ofResult = Optional.of(mess);
        when(messRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setLastname("Doe");
        customer.setMess(mess);
        customer.setPaid(true);
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult1 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult1);

        assertEquals(200.0f, calculatorService.calculateAmount(mess.getMessname(), startdate, endDate, customer.getUsername()));

    }


    @Test
    void testCalculateAmount3() {
        Optional<Mess> emptyResult = Optional.empty();
        when(messRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

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
        mess.setPricing("2000");
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
        customer.setPaid(true);
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        Date startDate = Date.from(LocalDate.of(2023, 10, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        assertThrows(UsernameNotFoundException.class, () -> calculatorService.calculateAmount("42", startDate,
                Date.from(LocalDate.of(2023, 10, 4).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Customer ID"));
        verify(messRepository).findById(Mockito.<String>any());
    }

    @Test
    void testCalculateTotal() {
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
        mess.setPricing("2000");
        mess.setReviews(new ArrayList<>());
        mess.setService("Service");
        mess.setTrial(true);
        mess.setType("Type");
        mess.setUsername("janedoe");
        Optional<Mess> ofResult = Optional.of(mess);
        when(messRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        AmountBalancesResponse actualCalculateTotalResult = calculatorService.calculateTotal("42");
        verify(messRepository).findById(Mockito.<String>any());
        assertEquals(0.0d, actualCalculateTotalResult.getPaid().doubleValue());
        assertEquals(0.0d, actualCalculateTotalResult.getRemaining().doubleValue());
        assertEquals(0.0d, actualCalculateTotalResult.getTotal().doubleValue());
    }

    @Test
    void testCalculateTotal2() {
        Optional<Mess> emptyResult = Optional.empty();
        when(messRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(UsernameNotFoundException.class, () -> calculatorService.calculateTotal("42"));
        verify(messRepository).findById(Mockito.<String>any());
    }


    @Test
    void testCalculateTotal3() {
        when(messRepository.findById(Mockito.<String>any())).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> calculatorService.calculateTotal("42"));
        verify(messRepository).findById(Mockito.<String>any());
    }

    @Test
    void testCalculateTotal4() {
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
        customer.setPaid(true);
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");

        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(customer);

        Mess mess2 = new Mess();
        mess2.setAboutSundays("About Sundays");
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setCustomers(customers);
        mess2.setFirstname("Jane");
        mess2.setLastname("Doe");
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("42");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        mess2.setUsername("janedoe");
        Optional<Mess> ofResult = Optional.of(mess2);
        when(messRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        AmountBalancesResponse actualCalculateTotalResult = calculatorService.calculateTotal("42");
        verify(messRepository).findById(Mockito.<String>any());
        assertEquals(0.0d, actualCalculateTotalResult.getRemaining().doubleValue());
        assertEquals(42.0d, actualCalculateTotalResult.getPaid().doubleValue());
        assertEquals(42.0d, actualCalculateTotalResult.getTotal().doubleValue());
    }

    @Test
    void testCalculateBMI() {
        assertEquals(0.1d, calculatorService.calculateBMI(10.0d, 10.0d).doubleValue());
        assertThrows(IllegalArgumentException.class, () -> calculatorService.calculateBMI(0.0d, 10.0d));
        assertEquals(10.0d, calculatorService.calculateBMI(1.0d, 10.0d).doubleValue());
        assertEquals(40.0d, calculatorService.calculateBMI(0.5d, 10.0d).doubleValue());
        assertThrows(IllegalArgumentException.class, () -> calculatorService.calculateBMI(10.0d, 0.0d));
    }

    @Test()
    void calculateAmountWhenStartDateNotAfterEndDateThrowsUsernameNotFoundException() {
        CalculatorService target = new CalculatorService(messRepository, customerRepository);
        doReturn(Optional.empty()).when(messRepository).findById("A");
        Date date = new Date();
        Date date2 = new Date();
        final UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class, () -> {
            target.calculateAmount("A", date, date2, "customerID1");
        });
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(messRepository).findById("A");
        });
    }
}
