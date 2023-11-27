package com.org.spemajorbackend.service;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;
import com.org.spemajorbackend.repository.ReviewRepository;

import java.util.List;

import com.org.spemajorbackend.repository.CustomerRepository;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.dro.ForgetPasswordRequest;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.repository.MenuRepository;
import com.org.spemajorbackend.entity.Menu;
import com.org.spemajorbackend.dro.AddReviewRequest;
import com.org.spemajorbackend.repository.JoiningRequestRepository;
import com.org.spemajorbackend.repository.AuthMasterRepository;
import com.org.spemajorbackend.repository.MessRepository;

import java.util.Optional;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.org.spemajorbackend.entity.Review;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Disabled;

@Timeout(value = 5)
class CustomerServiceTest2 {
    private final MessRepository messRepositoryMock = mock(MessRepository.class, "messRepository");
    private final MenuRepository menuRepositoryMock = mock(MenuRepository.class, "menuRepository");
    private final ReviewRepository reviewRepositoryMock = mock(ReviewRepository.class, "reviewRepository");
    private final CustomerRepository customerRepositoryMock = mock(CustomerRepository.class, "customerRepository");
    private final AuthMasterRepository authMasterRepositoryMock = mock(AuthMasterRepository.class, "authMasterRepository");
    private final JoiningRequestRepository joiningRequestRepositoryMock = mock(JoiningRequestRepository.class);

    @Disabled()
    @Test()
    void getMessListWhenMessesIsNotEmpty() {

        CustomerService target = new CustomerService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, joiningRequestRepositoryMock, reviewRepositoryMock, authMasterRepositoryMock);
        List list = new ArrayList<>();
        list.add((Mess) null);
        doReturn(list).when(messRepositoryMock).findAll();
        List<Menu> menuList = new ArrayList<>();
        doReturn(menuList).when(menuRepositoryMock).findByMess_Username("return_of_getUsername1");
        List<Review> reviewList = new ArrayList<>();
        doReturn(reviewList).when(reviewRepositoryMock).findByMess_Username("return_of_getUsername1");
        List<Mess> result = target.getMessList();
        List<Mess> messResultList = new ArrayList<>();
        messResultList.add((Mess) null);
        assertAll("result", () -> {
            assertThat(result.size(), equalTo(messResultList.size()));
            assertThat(result, containsInRelativeOrder(messResultList.toArray()));
            verify(messRepositoryMock).findAll();
            verify(menuRepositoryMock).findByMess_Username("return_of_getUsername1");
            verify(reviewRepositoryMock).findByMess_Username("return_of_getUsername1");
        });
    }

    @Test()
    void sendJoinRequestTest() throws Exception {
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

    @Test()
    void addReviewThrowsUsernameNotFoundException() {
        CustomerService target = new CustomerService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, joiningRequestRepositoryMock, reviewRepositoryMock, authMasterRepositoryMock);
        doReturn(Optional.empty()).when(messRepositoryMock).findById("A");
        AddReviewRequest addReviewRequest = new AddReviewRequest();
        addReviewRequest.setMess_owner_username("A");
        final UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class, () -> {
            target.addReview(addReviewRequest);
        });
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(messRepositoryMock).findById("A");
        });
    }

    @Test()
    void getProfileThrowsUsernameNotFoundException() {
        CustomerService target = new CustomerService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, joiningRequestRepositoryMock, reviewRepositoryMock, authMasterRepositoryMock);
        doReturn(Optional.empty()).when(customerRepositoryMock).findById("A");
        final UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class, () -> {
            target.getProfile("A");
        });
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(customerRepositoryMock).findById("A");
        });
    }

    @Test()
    void resetPasswordThrowsUsernameNotFoundException() {
        CustomerService target = new CustomerService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, joiningRequestRepositoryMock, reviewRepositoryMock, authMasterRepositoryMock);
        doReturn(Optional.empty()).when(authMasterRepositoryMock).findById("A");
        ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
        forgetPasswordRequest.setUsername("A");
        final UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class, () -> {
            target.resetPassword(forgetPasswordRequest);
        });
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(authMasterRepositoryMock).findById("A");
        });
    }
}
