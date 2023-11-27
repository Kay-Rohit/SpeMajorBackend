package com.org.spemajorbackend.service;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;
import com.org.spemajorbackend.repository.ReviewRepository;

import java.util.List;

import com.org.spemajorbackend.repository.CustomerRepository;
import com.org.spemajorbackend.dto.JoinRequestResponse;
import com.org.spemajorbackend.entity.Customer;
import com.org.spemajorbackend.dro.UpdateMessDetails;
import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.repository.MenuRepository;
import com.org.spemajorbackend.repository.JoiningRequestRepository;
import org.springframework.http.ResponseEntity;
import org.mockito.MockedStatic;
import com.org.spemajorbackend.entity.JoiningRequest;
import com.org.spemajorbackend.repository.MessRepository;

import java.util.Optional;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.org.spemajorbackend.dro.AddMenuRequest;

import static org.mockito.Mockito.doNothing;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mockStatic;

@Timeout(value = 5)
class MessServiceTest2 {
    private final MessRepository messRepositoryMock = mock(MessRepository.class, "messRepository");
    private final CustomerRepository customerRepositoryMock = mock(CustomerRepository.class, "customerRepository");
    private final JoiningRequestRepository requestRepositoryMock = mock(JoiningRequestRepository.class, "requestRepository");
    private final Customer customerMock = mock(Customer.class);
    private final MenuRepository menuRepositoryMock = mock(MenuRepository.class);
    private final Mess messMock = mock(Mess.class);
    private final ReviewRepository reviewRepositoryMock = mock(ReviewRepository.class);
    //Sapient generated method id: ${6cb47188-55b7-333a-829a-096f45914cb7}
    @Test()
    void addMenuItemsThrowsUsernameNotFoundException() {
        MessService target = new MessService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, requestRepositoryMock, reviewRepositoryMock);
        doReturn(Optional.empty()).when(messRepositoryMock).findById("A");
        List<AddMenuRequest> addMenuRequestList = new ArrayList<>();
        final UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class, () -> {
            target.addMenuItems(addMenuRequestList, "A");
        });
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(messRepositoryMock).findById("A");
        });
    }

    @Test()
    void acceptRequestThrowsUsernameNotFoundException() {
        MessService target = new MessService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, requestRepositoryMock, reviewRepositoryMock);
        doReturn(Optional.empty()).when(customerRepositoryMock).findById("A");
        final UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class, () -> {
            target.acceptRequest("ownerId1", "A");
        });

        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(customerRepositoryMock).findById("A");
        });
    }

    @Test()
    void getOwnerDetailsThrowsUsernameNotFoundException() {
        MessService target = new MessService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, requestRepositoryMock, reviewRepositoryMock);
        doReturn(Optional.empty()).when(messRepositoryMock).findById("A");
        final UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class, () -> {
            target.getOwnerDetails("A");
        });

        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(messRepositoryMock).findById("A");
        });
    }

    @Test()
    void updateOwnerDetailsThrowsUsernameNotFoundException() {
        MessService target = new MessService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, requestRepositoryMock, reviewRepositoryMock);
        doReturn(Optional.empty()).when(messRepositoryMock).findById("A");
        UpdateMessDetails updateMessDetailsMock = mock(UpdateMessDetails.class);
        final UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class, () -> {
            target.updateOwnerDetails("A", updateMessDetailsMock);
        });

        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(messRepositoryMock).findById("A");
        });
    }

    @Test()
    void seeJoiningRequestsWhenRequestsIsNotEmpty() {

        MessService target = new MessService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, requestRepositoryMock, reviewRepositoryMock);
        Customer customer = new Customer("username1", "firstname1", "lastname1", "email1", "phone1", false);
        Mess mess = new Mess("username1", "firstname1", "lastname1", "phone1", "messname1", "address1", "latitude1", "longitude1", "service1", "type1", false, false, "aboutSundays1", "pricing1");
        JoiningRequest joiningRequest = new JoiningRequest(customer, mess);
        List<JoiningRequest> joiningRequestList = new ArrayList<>();
        joiningRequestList.add(joiningRequest);
        doReturn(joiningRequestList).when(requestRepositoryMock).findByMess_Username("ownerId1");

        List<JoinRequestResponse> result = target.seeJoiningRequests("ownerId1");
        JoinRequestResponse joinRequestResponse = new JoinRequestResponse("username1", "username1");
        List<JoinRequestResponse> joinRequestResponseResultList = new ArrayList<>();
        joinRequestResponseResultList.add(joinRequestResponse);

        assertAll("result", () -> {
            assertThat(result.size(), equalTo(joinRequestResponseResultList.size()));
            assertThat(result, containsInRelativeOrder(joinRequestResponseResultList.toArray()));
            verify(requestRepositoryMock).findByMess_Username("ownerId1");
        });
    }

    @Test()
    void rejectRequestTest() throws Exception {
        MessService target = new MessService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, requestRepositoryMock, reviewRepositoryMock);
        doReturn(Optional.of(customerMock)).when(customerRepositoryMock).findById("customerId1");
        doReturn(Optional.of(messMock)).when(messRepositoryMock).findById("ownerId1");
        doNothing().when(requestRepositoryMock).deleteByCustomer_UsernameAndMess_Username(customerMock, messMock);

        ResponseEntity<?> result = target.rejectRequest("ownerId1", "customerId1");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("Request deleted");

        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntity));
            verify(customerRepositoryMock).findById("customerId1");
            verify(messRepositoryMock).findById("ownerId1");
            verify(requestRepositoryMock).deleteByCustomer_UsernameAndMess_Username(customerMock, messMock);
        });
    }

    @Test()
    void rejectRequestWhenCaughtException() throws Exception {
        try (MockedStatic<ResponseEntity> responseEntity = mockStatic(ResponseEntity.class, CALLS_REAL_METHODS)) {
            RuntimeException runtimeException = new RuntimeException("A");
            responseEntity.when(() -> ResponseEntity.ok("Request deleted")).thenThrow(runtimeException);
            MessService target = new MessService(messRepositoryMock, menuRepositoryMock, customerRepositoryMock, requestRepositoryMock, reviewRepositoryMock);
            doReturn(Optional.of(customerMock)).when(customerRepositoryMock).findById("customer1");
            doReturn(Optional.of(messMock)).when(messRepositoryMock).findById("mess1");
            doNothing().when(requestRepositoryMock).deleteByCustomer_UsernameAndMess_Username(customerMock, messMock);
            ResponseEntity<?> result = target.rejectRequest("mess1", "customer1");
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.badRequest();
            ResponseEntity<?> responseEntity2 = bodyBuilder.body("A");
            assertAll("result", () -> {
                assertThat(result, equalTo(responseEntity2));
                responseEntity.verify(() -> ResponseEntity.ok("Request deleted"), atLeast(1));
                verify(customerRepositoryMock, atLeast(1)).findById("customer1");
                verify(messRepositoryMock, atLeast(1)).findById("mess1");
                verify(requestRepositoryMock, atLeast(1)).deleteByCustomer_UsernameAndMess_Username(customerMock, messMock);
            });
        }
    }
}
