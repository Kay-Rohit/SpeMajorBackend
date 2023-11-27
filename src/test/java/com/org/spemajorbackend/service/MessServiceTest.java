package com.org.spemajorbackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.org.spemajorbackend.dro.AddMenuRequest;
import com.org.spemajorbackend.dro.UpdateMessDetails;
import com.org.spemajorbackend.dto.JoinRequestResponse;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.entity.JoiningRequest;
import com.org.spemajorbackend.entity.Menu;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.entity.Review;
import com.org.spemajorbackend.repository.CustomerRepository;
import com.org.spemajorbackend.repository.JoiningRequestRepository;
import com.org.spemajorbackend.repository.MenuRepository;
import com.org.spemajorbackend.repository.MessRepository;
import com.org.spemajorbackend.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MessService.class})
@ExtendWith(SpringExtension.class)
class MessServiceTest {
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private JoiningRequestRepository joiningRequestRepository;
    @MockBean
    private MenuRepository menuRepository;
    @MockBean
    private MessRepository messRepository;
    @Autowired
    private MessService messService;
    @MockBean
    private ReviewRepository reviewRepository;

    @Test
    void testAddMenuItems() {
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
        boolean actualAddMenuItemsResult = messService.addMenuItems(new ArrayList<>(), "janedoe");
        verify(messRepository).findById(Mockito.<String>any());
        assertTrue(actualAddMenuItemsResult);
    }

    @Test
    void testAddMenuItems2() {
        Optional<Mess> emptyResult = Optional.empty();
        when(messRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(UsernameNotFoundException.class, () -> messService.addMenuItems(new ArrayList<>(), "janedoe"));
        verify(messRepository).findById(Mockito.<String>any());
    }

    @Test
    void testAddMenuItems3() {
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

        Mess mess2 = new Mess();
        mess2.setAboutSundays("About Sundays");
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setCustomers(new ArrayList<>());
        mess2.setFirstname("Jane");
        mess2.setLastname("Doe");
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        mess2.setUsername("janedoe");

        Menu menu = new Menu();
        menu.setBreakfast("Breakfast");
        menu.setDay("Day");
        menu.setDinner("Dinner");
        menu.setId(1L);
        menu.setLunch("Lunch");
        menu.setMess(mess2);
        when(menuRepository.updateMenuByDay(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenReturn(1);
        when(menuRepository.findByMess_UsernameAndAndDay(Mockito.<String>any(), Mockito.<String>any())).thenReturn(menu);

        AddMenuRequest addMenuRequest = new AddMenuRequest();
        addMenuRequest.setBreakfast("Breakfast");
        addMenuRequest.setDay("Day");
        addMenuRequest.setDinner("Dinner");
        addMenuRequest.setLunch("Lunch");

        ArrayList<AddMenuRequest> menuItems = new ArrayList<>();
        menuItems.add(addMenuRequest);
        boolean actualAddMenuItemsResult = messService.addMenuItems(menuItems, "janedoe");
        verify(menuRepository).findByMess_UsernameAndAndDay(Mockito.<String>any(), Mockito.<String>any());
        verify(menuRepository).updateMenuByDay(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        assertTrue(actualAddMenuItemsResult);
    }

    @Test
    void testAddMenuItems4() {
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

        Mess mess2 = new Mess();
        mess2.setAboutSundays("About Sundays");
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setCustomers(new ArrayList<>());
        mess2.setFirstname("Jane");
        mess2.setLastname("Doe");
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        mess2.setUsername("janedoe");

        Menu menu = new Menu();
        menu.setBreakfast("Breakfast");
        menu.setDay("Day");
        menu.setDinner("Dinner");
        menu.setId(1L);
        menu.setLunch("Lunch");
        menu.setMess(mess2);
        when(menuRepository.updateMenuByDay(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any())).thenThrow(new RuntimeException("foo"));
        when(menuRepository.findByMess_UsernameAndAndDay(Mockito.<String>any(), Mockito.<String>any())).thenReturn(menu);

        AddMenuRequest addMenuRequest = new AddMenuRequest();
        addMenuRequest.setBreakfast("Breakfast");
        addMenuRequest.setDay("Day");
        addMenuRequest.setDinner("Dinner");
        addMenuRequest.setLunch("Lunch");

        ArrayList<AddMenuRequest> menuItems = new ArrayList<>();
        menuItems.add(addMenuRequest);
        boolean actualAddMenuItemsResult = messService.addMenuItems(menuItems, "janedoe");
        verify(menuRepository).findByMess_UsernameAndAndDay(Mockito.<String>any(), Mockito.<String>any());
        verify(menuRepository).updateMenuByDay(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        assertFalse(actualAddMenuItemsResult);
    }

    @Test
    void testAcceptRequest() {
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

        Mess mess2 = new Mess();
        mess2.setAboutSundays("About Sundays");
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setCustomers(new ArrayList<>());
        mess2.setFirstname("Jane");
        mess2.setLastname("Doe");
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        mess2.setUsername("janedoe");
        when(messRepository.save(Mockito.<Mess>any())).thenReturn(mess2);
        when(messRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        Mess mess3 = new Mess();
        mess3.setAboutSundays("About Sundays");
        mess3.setAddress("42 Main St");
        mess3.setBreakfast(true);
        mess3.setCustomers(new ArrayList<>());
        mess3.setFirstname("Jane");
        mess3.setLastname("Doe");
        mess3.setLatitude("Latitude");
        mess3.setLongitude("Longitude");
        mess3.setMenus(new ArrayList<>());
        mess3.setMessname("Messname");
        mess3.setPhone("6625550144");
        mess3.setPricing("Pricing");
        mess3.setReviews(new ArrayList<>());
        mess3.setService("Service");
        mess3.setTrial(true);
        mess3.setType("Type");
        mess3.setUsername("janedoe");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setLastname("Doe");
        customer.setMess(mess3);
        customer.setPaid(true);
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult2 = Optional.of(customer);

        Mess mess4 = new Mess();
        mess4.setAboutSundays("About Sundays");
        mess4.setAddress("42 Main St");
        mess4.setBreakfast(true);
        mess4.setCustomers(new ArrayList<>());
        mess4.setFirstname("Jane");
        mess4.setLastname("Doe");
        mess4.setLatitude("Latitude");
        mess4.setLongitude("Longitude");
        mess4.setMenus(new ArrayList<>());
        mess4.setMessname("Messname");
        mess4.setPhone("6625550144");
        mess4.setPricing("Pricing");
        mess4.setReviews(new ArrayList<>());
        mess4.setService("Service");
        mess4.setTrial(true);
        mess4.setType("Type");
        mess4.setUsername("janedoe");

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setFirstname("Jane");
        customer2.setLastname("Doe");
        customer2.setMess(mess4);
        customer2.setPaid(true);
        customer2.setPhone("6625550144");
        customer2.setUsername("janedoe");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        when(joiningRequestRepository.findByCustomer_Username(Mockito.<String>any())).thenReturn(new ArrayList<>());
        doNothing().when(joiningRequestRepository).deleteAll(Mockito.<Iterable<JoiningRequest>>any());
        ResponseEntity<?> actualAcceptRequestResult = messService.acceptRequest("42", "42");
        verify(joiningRequestRepository).findByCustomer_Username(Mockito.<String>any());
        verify(joiningRequestRepository).deleteAll(Mockito.<Iterable<JoiningRequest>>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(messRepository).save(Mockito.<Mess>any());
        assertEquals("Request Accepted Successfully", actualAcceptRequestResult.getBody());
        assertEquals(HttpStatus.OK, actualAcceptRequestResult.getStatusCode());
        assertTrue(actualAcceptRequestResult.getHeaders().isEmpty());
    }

    @Test
    void testAcceptRequest2() {
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

        Mess mess2 = new Mess();
        mess2.setAboutSundays("About Sundays");
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setCustomers(new ArrayList<>());
        mess2.setFirstname("Jane");
        mess2.setLastname("Doe");
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        mess2.setUsername("janedoe");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setLastname("Doe");
        customer.setMess(mess2);
        customer.setPaid(true);
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        when(joiningRequestRepository.findByCustomer_Username(Mockito.<String>any()))
                .thenThrow(new UsernameNotFoundException("Request Accepted Successfully"));
        ResponseEntity<?> actualAcceptRequestResult = messService.acceptRequest("42", "42");
        verify(joiningRequestRepository).findByCustomer_Username(Mockito.<String>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        assertEquals("Request Accepted Successfully", actualAcceptRequestResult.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualAcceptRequestResult.getStatusCode());
        assertTrue(actualAcceptRequestResult.getHeaders().isEmpty());
    }

    @Test
    void testGetOwnerDetails() {
        Mess mess = new Mess();
        mess.setAboutSundays("About Sundays");
        mess.setAddress("42 Main St");
        mess.setBreakfast(true);
        ArrayList<Customer> customerList = new ArrayList<>();
        mess.setCustomers(customerList);
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
        when(messRepository.findById((String) Mockito.any())).thenReturn(ofResult);
        when(menuRepository.findByMess_Username((String) Mockito.any())).thenReturn(new ArrayList<>());
        when(customerRepository.findByMess_Username((String) Mockito.any())).thenReturn(new ArrayList<>());
        when(reviewRepository.findByMess_Username((String) Mockito.any())).thenReturn(new ArrayList<>());
        ResponseEntity<?> actualOwnerDetails = messService.getOwnerDetails("Owner id");
        assertTrue(actualOwnerDetails.hasBody());
        assertTrue(actualOwnerDetails.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualOwnerDetails.getStatusCode());
        assertEquals(customerList, ((Mess) actualOwnerDetails.getBody()).getReviews());
        assertEquals(customerList, ((Mess) actualOwnerDetails.getBody()).getMenus());
        assertEquals(customerList, ((Mess) actualOwnerDetails.getBody()).getCustomers());
        verify(messRepository).findById((String) Mockito.any());
        verify(menuRepository).findByMess_Username((String) Mockito.any());
        verify(customerRepository).findByMess_Username((String) Mockito.any());
        verify(reviewRepository).findByMess_Username((String) Mockito.any());
    }

    @Test
    void testGetOwnerDetails2() {
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
        when(messRepository.findById((String) Mockito.any())).thenReturn(ofResult);
        when(menuRepository.findByMess_Username((String) Mockito.any())).thenReturn(new ArrayList<>());
        when(customerRepository.findByMess_Username((String) Mockito.any())).thenReturn(new ArrayList<>());
        when(reviewRepository.findByMess_Username((String) Mockito.any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> messService.getOwnerDetails("Owner id"));
        verify(messRepository).findById((String) Mockito.any());
        verify(menuRepository).findByMess_Username((String) Mockito.any());
        verify(customerRepository).findByMess_Username((String) Mockito.any());
        verify(reviewRepository).findByMess_Username((String) Mockito.any());
    }

    @Test
    void testGetOwnerDetails3() {
        when(messRepository.findById((String) Mockito.any())).thenReturn(Optional.empty());
        when(menuRepository.findByMess_Username((String) Mockito.any())).thenReturn(new ArrayList<>());
        when(customerRepository.findByMess_Username((String) Mockito.any())).thenReturn(new ArrayList<>());
        when(reviewRepository.findByMess_Username((String) Mockito.any())).thenReturn(new ArrayList<>());
        assertThrows(UsernameNotFoundException.class, () -> messService.getOwnerDetails("Owner id"));
        verify(messRepository).findById((String) Mockito.any());
    }

    @Test
    void testUpdateOwnerDetails() {
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

        Mess mess2 = new Mess();
        mess2.setAboutSundays("About Sundays");
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setCustomers(new ArrayList<>());
        mess2.setFirstname("Jane");
        mess2.setLastname("Doe");
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        mess2.setUsername("janedoe");
        when(messRepository.save(Mockito.<Mess>any())).thenReturn(mess2);
        when(messRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        UpdateMessDetails mess3 = new UpdateMessDetails();
        mess3.setAddress("42 Main St");
        mess3.setBreakfast(true);
        mess3.setLatitude("Latitude");
        mess3.setLongitude("Longitude");
        mess3.setMessname("Messname");
        mess3.setPhone("6625550144");
        mess3.setPricing("Pricing");
        mess3.setService("Service");
        mess3.setTrial(true);
        mess3.setType("Type");
        ResponseEntity<?> actualUpdateOwnerDetailsResult = messService.updateOwnerDetails("Owner id", mess3);
        verify(messRepository).findById(Mockito.<String>any());
        verify(messRepository).save(Mockito.<Mess>any());
        assertEquals("42 Main St", ((Mess) actualUpdateOwnerDetailsResult.getBody()).getAddress());
        assertEquals("6625550144", ((Mess) actualUpdateOwnerDetailsResult.getBody()).getPhone());
        assertEquals("Latitude", ((Mess) actualUpdateOwnerDetailsResult.getBody()).getLatitude());
        assertEquals("Longitude", ((Mess) actualUpdateOwnerDetailsResult.getBody()).getLongitude());
        assertEquals("Messname", ((Mess) actualUpdateOwnerDetailsResult.getBody()).getMessname());
        assertEquals("Pricing", ((Mess) actualUpdateOwnerDetailsResult.getBody()).getPricing());
        assertEquals("Service", ((Mess) actualUpdateOwnerDetailsResult.getBody()).getService());
        assertEquals("Type", ((Mess) actualUpdateOwnerDetailsResult.getBody()).getType());
        assertEquals(HttpStatus.OK, actualUpdateOwnerDetailsResult.getStatusCode());
        assertTrue(((Mess) actualUpdateOwnerDetailsResult.getBody()).isBreakfast());
        assertTrue(((Mess) actualUpdateOwnerDetailsResult.getBody()).isTrial());
        assertTrue(actualUpdateOwnerDetailsResult.hasBody());
        assertTrue(actualUpdateOwnerDetailsResult.getHeaders().isEmpty());
    }

    @Test
    void testUpdateOwnerDetails2() {
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
        when(messRepository.save(Mockito.<Mess>any())).thenThrow(new RuntimeException("6625550144"));
        when(messRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        UpdateMessDetails mess2 = new UpdateMessDetails();
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        assertThrows(RuntimeException.class, () -> messService.updateOwnerDetails("Owner id", mess2));
        verify(messRepository).findById(Mockito.<String>any());
        verify(messRepository).save(Mockito.<Mess>any());
    }

    @Test
    void testUpdateOwnerDetails3() {
        Mess mess = mock(Mess.class);
        when(mess.getPricing()).thenThrow(new RuntimeException("6625550144"));
        when(mess.getType()).thenReturn("Type");
        when(mess.getService()).thenReturn("Service");
        when(mess.getLongitude()).thenReturn("Longitude");
        when(mess.getLatitude()).thenReturn("Latitude");
        when(mess.getAddress()).thenReturn("42 Main St");
        when(mess.getMessname()).thenReturn("Messname");
        when(mess.getPhone()).thenReturn("6625550144");
        doNothing().when(mess).setAboutSundays(Mockito.<String>any());
        doNothing().when(mess).setAddress(Mockito.<String>any());
        doNothing().when(mess).setBreakfast(anyBoolean());
        doNothing().when(mess).setCustomers(Mockito.<List<Customer>>any());
        doNothing().when(mess).setFirstname(Mockito.<String>any());
        doNothing().when(mess).setLastname(Mockito.<String>any());
        doNothing().when(mess).setLatitude(Mockito.<String>any());
        doNothing().when(mess).setLongitude(Mockito.<String>any());
        doNothing().when(mess).setMenus(Mockito.<List<Menu>>any());
        doNothing().when(mess).setMessname(Mockito.<String>any());
        doNothing().when(mess).setPhone(Mockito.<String>any());
        doNothing().when(mess).setPricing(Mockito.<String>any());
        doNothing().when(mess).setReviews(Mockito.<List<Review>>any());
        doNothing().when(mess).setService(Mockito.<String>any());
        doNothing().when(mess).setTrial(anyBoolean());
        doNothing().when(mess).setType(Mockito.<String>any());
        doNothing().when(mess).setUsername(Mockito.<String>any());
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

        UpdateMessDetails mess2 = new UpdateMessDetails();
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        assertThrows(RuntimeException.class, () -> messService.updateOwnerDetails("Owner id", mess2));
        verify(mess).getAddress();
        verify(mess).getLatitude();
        verify(mess).getLongitude();
        verify(mess).getMessname();
        verify(mess).getPhone();
        verify(mess).getPricing();
        verify(mess).getService();
        verify(mess).getType();
        verify(mess).setAboutSundays(Mockito.<String>any());
        verify(mess, atLeast(1)).setAddress(Mockito.<String>any());
        verify(mess, atLeast(1)).setBreakfast(anyBoolean());
        verify(mess).setCustomers(Mockito.<List<Customer>>any());
        verify(mess).setFirstname(Mockito.<String>any());
        verify(mess).setLastname(Mockito.<String>any());
        verify(mess, atLeast(1)).setLatitude(Mockito.<String>any());
        verify(mess, atLeast(1)).setLongitude(Mockito.<String>any());
        verify(mess).setMenus(Mockito.<List<Menu>>any());
        verify(mess, atLeast(1)).setMessname(Mockito.<String>any());
        verify(mess, atLeast(1)).setPhone(Mockito.<String>any());
        verify(mess, atLeast(1)).setPricing(Mockito.<String>any());
        verify(mess).setReviews(Mockito.<List<Review>>any());
        verify(mess, atLeast(1)).setService(Mockito.<String>any());
        verify(mess, atLeast(1)).setTrial(anyBoolean());
        verify(mess, atLeast(1)).setType(Mockito.<String>any());
        verify(mess).setUsername(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
    }


    @Test
    void testSeeJoiningRequests() {
        when(joiningRequestRepository.findByMess_Username(Mockito.<String>any())).thenReturn(new ArrayList<>());
        List<JoinRequestResponse> actualSeeJoiningRequestsResult = messService.seeJoiningRequests("42");
        verify(joiningRequestRepository).findByMess_Username(Mockito.<String>any());
        assertTrue(actualSeeJoiningRequestsResult.isEmpty());
    }

    @Test
    void testSeeJoiningRequests2() {
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

        Mess mess2 = new Mess();
        mess2.setAboutSundays("About Sundays");
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setCustomers(new ArrayList<>());
        mess2.setFirstname("Jane");
        mess2.setLastname("Doe");
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        mess2.setUsername("janedoe");

        JoiningRequest joiningRequest = new JoiningRequest();
        joiningRequest.setCustomer(customer);
        joiningRequest.setId(UUID.randomUUID());
        joiningRequest.setMess(mess2);

        ArrayList<JoiningRequest> joiningRequestList = new ArrayList<>();
        joiningRequestList.add(joiningRequest);
        when(joiningRequestRepository.findByMess_Username(Mockito.<String>any())).thenReturn(joiningRequestList);
        List<JoinRequestResponse> actualSeeJoiningRequestsResult = messService.seeJoiningRequests("42");
        verify(joiningRequestRepository).findByMess_Username(Mockito.<String>any());
        JoinRequestResponse getResult = actualSeeJoiningRequestsResult.get(0);
        assertEquals("janedoe", getResult.getCustomer_username());
        assertEquals("janedoe", getResult.getMess_owner_username());
        assertEquals(1, actualSeeJoiningRequestsResult.size());
    }

    @Test
    void testSeeJoiningRequests3() {
        when(joiningRequestRepository.findByMess_Username(Mockito.<String>any())).thenThrow(new RuntimeException("foo"));
        assertThrows(RuntimeException.class, () -> messService.seeJoiningRequests("42"));
        verify(joiningRequestRepository).findByMess_Username(Mockito.<String>any());
    }

    @Test
    void testSeeJoiningRequests4() {
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

        Mess mess2 = new Mess();
        mess2.setAboutSundays("About Sundays");
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setCustomers(new ArrayList<>());
        mess2.setFirstname("Jane");
        mess2.setLastname("Doe");
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        mess2.setUsername("janedoe");

        Mess mess3 = new Mess();
        mess3.setAboutSundays("About Sundays");
        mess3.setAddress("42 Main St");
        mess3.setBreakfast(true);
        mess3.setCustomers(new ArrayList<>());
        mess3.setFirstname("Jane");
        mess3.setLastname("Doe");
        mess3.setLatitude("Latitude");
        mess3.setLongitude("Longitude");
        mess3.setMenus(new ArrayList<>());
        mess3.setMessname("Messname");
        mess3.setPhone("6625550144");
        mess3.setPricing("Pricing");
        mess3.setReviews(new ArrayList<>());
        mess3.setService("Service");
        mess3.setTrial(true);
        mess3.setType("Type");
        mess3.setUsername("janedoe");
        JoiningRequest joiningRequest = mock(JoiningRequest.class);
        when(joiningRequest.getCustomer()).thenThrow(new RuntimeException("foo"));
        when(joiningRequest.getMess()).thenReturn(mess3);
        doNothing().when(joiningRequest).setCustomer(Mockito.<Customer>any());
        doNothing().when(joiningRequest).setId(Mockito.<UUID>any());
        doNothing().when(joiningRequest).setMess(Mockito.<Mess>any());
        joiningRequest.setCustomer(customer);
        joiningRequest.setId(UUID.randomUUID());
        joiningRequest.setMess(mess2);

        ArrayList<JoiningRequest> joiningRequestList = new ArrayList<>();
        joiningRequestList.add(joiningRequest);
        when(joiningRequestRepository.findByMess_Username(Mockito.<String>any())).thenReturn(joiningRequestList);
        assertThrows(RuntimeException.class, () -> messService.seeJoiningRequests("42"));
        verify(joiningRequest).getCustomer();
        verify(joiningRequest).getMess();
        verify(joiningRequest).setCustomer(Mockito.<Customer>any());
        verify(joiningRequest).setId(Mockito.<UUID>any());
        verify(joiningRequest).setMess(Mockito.<Mess>any());
        verify(joiningRequestRepository).findByMess_Username(Mockito.<String>any());
    }

    @Test
    void testRejectRequest() {
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

        Mess mess2 = new Mess();
        mess2.setAboutSundays("About Sundays");
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setCustomers(new ArrayList<>());
        mess2.setFirstname("Jane");
        mess2.setLastname("Doe");
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        mess2.setUsername("janedoe");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setLastname("Doe");
        customer.setMess(mess2);
        customer.setPaid(true);
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        doNothing().when(joiningRequestRepository)
                .deleteByCustomer_UsernameAndMess_Username(Mockito.<Customer>any(), Mockito.<Mess>any());
        ResponseEntity<?> actualRejectRequestResult = messService.rejectRequest("42", "42");
        verify(joiningRequestRepository).deleteByCustomer_UsernameAndMess_Username(Mockito.<Customer>any(),
                Mockito.<Mess>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        assertEquals("Request deleted", actualRejectRequestResult.getBody());
        assertEquals(HttpStatus.OK, actualRejectRequestResult.getStatusCode());
        assertTrue(actualRejectRequestResult.getHeaders().isEmpty());
    }

    @Test
    void testRejectRequest2() {
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

        Mess mess2 = new Mess();
        mess2.setAboutSundays("About Sundays");
        mess2.setAddress("42 Main St");
        mess2.setBreakfast(true);
        mess2.setCustomers(new ArrayList<>());
        mess2.setFirstname("Jane");
        mess2.setLastname("Doe");
        mess2.setLatitude("Latitude");
        mess2.setLongitude("Longitude");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("Messname");
        mess2.setPhone("6625550144");
        mess2.setPricing("Pricing");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("Service");
        mess2.setTrial(true);
        mess2.setType("Type");
        mess2.setUsername("janedoe");

        Customer customer = new Customer();
        customer.setEmail("jane.doe@example.org");
        customer.setFirstname("Jane");
        customer.setLastname("Doe");
        customer.setMess(mess2);
        customer.setPaid(true);
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        doThrow(new RuntimeException("Request deleted")).when(joiningRequestRepository)
                .deleteByCustomer_UsernameAndMess_Username(Mockito.<Customer>any(), Mockito.<Mess>any());
        ResponseEntity<?> actualRejectRequestResult = messService.rejectRequest("42", "42");
        verify(joiningRequestRepository).deleteByCustomer_UsernameAndMess_Username(Mockito.<Customer>any(),
                Mockito.<Mess>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        assertEquals("Request deleted", actualRejectRequestResult.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualRejectRequestResult.getStatusCode());
        assertTrue(actualRejectRequestResult.getHeaders().isEmpty());
    }
}
