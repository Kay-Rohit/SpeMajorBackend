package com.org.spemajorbackend.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

import com.org.spemajorbackend.dro.AddReviewRequest;
import com.org.spemajorbackend.dro.ForgetPasswordRequest;
import com.org.spemajorbackend.dto.CustomerProfileResponse;
import com.org.spemajorbackend.entity.AuthMaster;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.entity.JoiningRequest;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.entity.Review;
import com.org.spemajorbackend.repository.AuthMasterRepository;
import com.org.spemajorbackend.repository.CustomerRepository;
import com.org.spemajorbackend.repository.JoiningRequestRepository;
import com.org.spemajorbackend.repository.MenuRepository;
import com.org.spemajorbackend.repository.MessRepository;
import com.org.spemajorbackend.repository.ReviewRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerService.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @MockBean
    private AuthMasterRepository authMasterRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @MockBean
    private JoiningRequestRepository joiningRequestRepository;

    @MockBean
    private MenuRepository menuRepository;

    @MockBean
    private MessRepository messRepository;

    @MockBean
    private ReviewRepository reviewRepository;

    private final MessRepository messRepositoryMock = mock(MessRepository.class, "messRepository");

    private final MenuRepository menuRepositoryMock = mock(MenuRepository.class, "menuRepository");

    private final ReviewRepository reviewRepositoryMock = mock(ReviewRepository.class, "reviewRepository");

    private final CustomerRepository customerRepositoryMock = mock(CustomerRepository.class, "customerRepository");

    private final AuthMasterRepository authMasterRepositoryMock = mock(AuthMasterRepository.class, "authMasterRepository");

    private final JoiningRequestRepository joiningRequestRepositoryMock = mock(JoiningRequestRepository.class);

    @Test()
    void sendJoinRequestTest2() throws Exception {
        CustomerService target = new CustomerService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, joiningRequestRepositoryMock, reviewRepositoryMock, authMasterRepositoryMock);
        Customer customerMock = mock(Customer.class);
        doReturn(Optional.of(customerMock)).when(customerRepositoryMock).findById("customerId1");
        doReturn(Optional.empty()).when(messRepositoryMock).findById("A");
        boolean result = target.sendJoinRequest("customerId1", "A");

        assertAll("result", () -> {
            assertThat(result, equalTo(Boolean.FALSE));
            verify(customerRepositoryMock).findById("customerId1");
            verify(messRepositoryMock).findById("A");
        });
    }

    @Test
    void testGetMessList() {
        when(messRepository.findAll()).thenReturn(new ArrayList<>());
        List<Mess> actualMessList = customerService.getMessList();
        verify(messRepository).findAll();
        assertTrue(actualMessList.isEmpty());
    }


    @Test
    void testGetMessList2() {
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

        ArrayList<Mess> messList = new ArrayList<>();
        messList.add(mess);
        when(messRepository.findAll()).thenReturn(messList);
        when(menuRepository.findByMess_Username(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(reviewRepository.findByMess_Username(Mockito.<String>any())).thenReturn(new ArrayList<>());
        List<Mess> actualMessList = customerService.getMessList();
        verify(menuRepository).findByMess_Username(Mockito.<String>any());
        verify(reviewRepository).findByMess_Username(Mockito.<String>any());
        verify(messRepository).findAll();
        assertEquals(1, actualMessList.size());
    }

    @Test
    void testGetMessList3() {
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

        ArrayList<Mess> messList = new ArrayList<>();
        messList.add(mess);
        when(messRepository.findAll()).thenReturn(messList);
        when(menuRepository.findByMess_Username(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(reviewRepository.findByMess_Username(Mockito.<String>any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> customerService.getMessList());
        verify(menuRepository).findByMess_Username(Mockito.<String>any());
        verify(reviewRepository).findByMess_Username(Mockito.<String>any());
        verify(messRepository).findAll();
    }

    @Test
    void testGetMessList4() {
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

        Mess mess2 = new Mess();
        mess2.setAboutSundays("com.org.spemajorbackend.entity.Mess");
        mess2.setAddress("17 High St");
        mess2.setBreakfast(false);
        mess2.setCustomers(new ArrayList<>());
        mess2.setFirstname("John");
        mess2.setLastname("Smith");
        mess2.setLatitude("com.org.spemajorbackend.entity.Mess");
        mess2.setLongitude("com.org.spemajorbackend.entity.Mess");
        mess2.setMenus(new ArrayList<>());
        mess2.setMessname("com.org.spemajorbackend.entity.Mess");
        mess2.setPhone("8605550118");
        mess2.setPricing("com.org.spemajorbackend.entity.Mess");
        mess2.setReviews(new ArrayList<>());
        mess2.setService("com.org.spemajorbackend.entity.Mess");
        mess2.setTrial(false);
        mess2.setType("com.org.spemajorbackend.entity.Mess");
        mess2.setUsername("Username");

        ArrayList<Mess> messList = new ArrayList<>();
        messList.add(mess2);
        messList.add(mess);
        when(messRepository.findAll()).thenReturn(messList);
        when(menuRepository.findByMess_Username(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(reviewRepository.findByMess_Username(Mockito.<String>any())).thenReturn(new ArrayList<>());
        List<Mess> actualMessList = customerService.getMessList();
        verify(menuRepository, atLeast(1)).findByMess_Username(Mockito.<String>any());
        verify(reviewRepository, atLeast(1)).findByMess_Username(Mockito.<String>any());
        verify(messRepository).findAll();
        assertEquals(2, actualMessList.size());
    }

    @Test
    void testSendJoinRequest() {
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
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);

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

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setFirstname("Jane");
        customer2.setLastname("Doe");
        customer2.setMess(mess3);
        customer2.setPhone("6625550144");
        customer2.setUsername("janedoe");

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

        JoiningRequest joiningRequest = new JoiningRequest();
        joiningRequest.setCustomer(customer2);
        joiningRequest.setId(UUID.randomUUID());
        joiningRequest.setMess(mess4);
        when(joiningRequestRepository.save(Mockito.<JoiningRequest>any())).thenReturn(joiningRequest);
        boolean actualSendJoinRequestResult = customerService.sendJoinRequest("42", "42");
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        verify(joiningRequestRepository).save(Mockito.<JoiningRequest>any());
        assertTrue(actualSendJoinRequestResult);
    }


    @Test
    void testSendJoinRequest2() {
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
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        when(joiningRequestRepository.save(Mockito.<JoiningRequest>any())).thenThrow(new UsernameNotFoundException("Msg"));
        boolean actualSendJoinRequestResult = customerService.sendJoinRequest("42", "42");
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        verify(joiningRequestRepository).save(Mockito.<JoiningRequest>any());
        assertFalse(actualSendJoinRequestResult);
    }


    @Test
    void testSendJoinRequest3() {
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
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        boolean actualSendJoinRequestResult = customerService.sendJoinRequest("42", "42");
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        assertFalse(actualSendJoinRequestResult);
    }

    @Test
    void testSendJoinRequest4() {
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
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        boolean actualSendJoinRequestResult = customerService.sendJoinRequest("42", "42");
        verify(customerRepository).findById(Mockito.<String>any());
        assertFalse(actualSendJoinRequestResult);
    }


    @Test
    void testAddReview() {
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
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);

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

        Customer customer2 = new Customer();
        customer2.setEmail("jane.doe@example.org");
        customer2.setFirstname("Jane");
        customer2.setLastname("Doe");
        customer2.setMess(mess3);
        customer2.setPhone("6625550144");
        customer2.setUsername("janedoe");

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

        Review review = new Review();
        review.setComment("Comment");
        review.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        review.setCustomer(customer2);
        review.setId(UUID.randomUUID());
        review.setMess(mess4);
        review.setRating(1);
        review.setReviewBy("Review By");
        when(reviewRepository.save(Mockito.<Review>any())).thenReturn(review);
        when(reviewRepository.findByCustomer_UsernameAndMess_Username(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        AddReviewRequest review2 = new AddReviewRequest();
        review2.setComment("Comment");
        review2.setCustomer_username("janedoe");
        review2.setMess_owner_username("janedoe");
        review2.setRating(1);
        boolean actualAddReviewResult = customerService.addReview(review2);
        verify(reviewRepository).findByCustomer_UsernameAndMess_Username(Mockito.<String>any(), Mockito.<String>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        verify(reviewRepository).save(Mockito.<Review>any());
        assertTrue(actualAddReviewResult);
    }


    @Test
    void testAddReview2() {
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
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        when(reviewRepository.findByCustomer_UsernameAndMess_Username(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new UsernameNotFoundException("Msg"));

        AddReviewRequest review = new AddReviewRequest();
        review.setComment("Comment");
        review.setCustomer_username("janedoe");
        review.setMess_owner_username("janedoe");
        review.setRating(1);
        boolean actualAddReviewResult = customerService.addReview(review);
        verify(reviewRepository).findByCustomer_UsernameAndMess_Username(Mockito.<String>any(), Mockito.<String>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        assertFalse(actualAddReviewResult);
    }


    @Test
    void testAddReview3() {
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
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        AddReviewRequest review = new AddReviewRequest();
        review.setComment("Comment");
        review.setCustomer_username("janedoe");
        review.setMess_owner_username("janedoe");
        review.setRating(1);
        assertThrows(UsernameNotFoundException.class, () -> customerService.addReview(review));
        verify(messRepository).findById(Mockito.<String>any());
    }

    @Test
    void testGetProfile() {
        Mess mess = new Mess();
        mess.setAboutSundays("About Sundays");
        mess.setAddress("42 Main St");
        mess.setBreakfast(true);
        ArrayList<Customer> customers = new ArrayList<>();
        mess.setCustomers(customers);
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
        when(menuRepository.findByMess_Username(Mockito.<String>any())).thenReturn(new ArrayList<>());

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
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        CustomerProfileResponse actualProfile = customerService.getProfile("42");
        verify(menuRepository).findByMess_Username(Mockito.<String>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
        assertEquals("42 Main St", actualProfile.getAddress());
        assertEquals("6625550144", actualProfile.getOwnerPhone());
        assertEquals("6625550144", actualProfile.getPhone());
        assertEquals("Doe", actualProfile.getLastname());
        assertEquals("Doe", actualProfile.getOwnerLastname());
        assertEquals("Jane", actualProfile.getFirstname());
        assertEquals("Jane", actualProfile.getOwnerFirstname());
        assertEquals("Messname", actualProfile.getMessname());
        assertEquals("jane.doe@example.org", actualProfile.getEmail());
        assertEquals("janedoe", actualProfile.getOwnerUsername());
        assertEquals("janedoe", actualProfile.getUsername());
        assertEquals(customers, actualProfile.getMenus());
    }

    @Test
    void testGetProfile2() {
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
        when(menuRepository.findByMess_Username(Mockito.<String>any())).thenThrow(new UsernameNotFoundException("Msg"));

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
        customer.setPhone("6625550144");
        customer.setUsername("janedoe");
        Optional<Customer> ofResult2 = Optional.of(customer);
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult2);
        assertThrows(UsernameNotFoundException.class, () -> customerService.getProfile("42"));
        verify(menuRepository).findByMess_Username(Mockito.<String>any());
        verify(customerRepository).findById(Mockito.<String>any());
        verify(messRepository).findById(Mockito.<String>any());
    }


    @Test
    void testResetPassword() {
        AuthMaster authMaster = new AuthMaster();
        authMaster.setPassword("iloveyou");
        authMaster.setRole("Role");
        authMaster.setUsername("janedoe");
        Optional<AuthMaster> ofResult = Optional.of(authMaster);
        when(authMasterRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        boolean actualResetPasswordResult = customerService
                .resetPassword(new ForgetPasswordRequest("janedoe", "iloveyou", "iloveyou"));
        verify(authMasterRepository).findById(Mockito.<String>any());
        assertFalse(actualResetPasswordResult);
    }


    @Test
    void testResetPassword2() {
        Optional<AuthMaster> emptyResult = Optional.empty();
        when(authMasterRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(UsernameNotFoundException.class,
                () -> customerService.resetPassword(new ForgetPasswordRequest("janedoe", "iloveyou", "iloveyou")));
        verify(authMasterRepository).findById(Mockito.<String>any());
    }


    @Test
    void testResetPassword3() {
        when(authMasterRepository.findById(Mockito.<String>any())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class,
                () -> customerService.resetPassword(new ForgetPasswordRequest("janedoe", "iloveyou", "iloveyou")));
        verify(authMasterRepository).findById(Mockito.<String>any());
    }
}
