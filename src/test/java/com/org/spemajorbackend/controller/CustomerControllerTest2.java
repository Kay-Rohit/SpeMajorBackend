package com.org.spemajorbackend.controller;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;

import java.util.List;

import com.org.spemajorbackend.dro.ForgetPasswordRequest;

import java.util.Date;

import com.org.spemajorbackend.entity.Mess;
import com.org.spemajorbackend.dto.CustomerProfileResponse;
import com.org.spemajorbackend.service.CalculatorService;
import com.org.spemajorbackend.service.CustomerService;
import com.org.spemajorbackend.dro.AddReviewRequest;
import org.springframework.http.ResponseEntity;
import org.mockito.MockedStatic;

import java.util.ArrayList;

import com.org.spemajorbackend.dro.AmountRequest;

import static org.mockito.ArgumentMatchers.anyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.doReturn;

@Timeout(value = 5)
class CustomerControllerTest2 {
    private final CustomerService customerServiceMock = mock(CustomerService.class, "customerService");
    private final CalculatorService calculatorServiceMock = mock(CalculatorService.class, "calculatorService");
    private final CustomerProfileResponse customerProfileResponseMock = mock(CustomerProfileResponse.class);
    private final ForgetPasswordRequest forgetPasswordRequestMock = mock(ForgetPasswordRequest.class);

    @Test()
    void getMessListTest() {
        ResponseEntity<List> responseEntityMock = mock(ResponseEntity.class);
        try (MockedStatic<ResponseEntity> responseEntity = mockStatic(ResponseEntity.class)) {
            responseEntity.when(() -> ResponseEntity.ok(anyList())).thenReturn(responseEntityMock);
            CustomerController target = new CustomerController(customerServiceMock, calculatorServiceMock);
            List<Mess> messList = new ArrayList<>();
            doReturn(messList).when(customerServiceMock).getMessList();
            ResponseEntity<?> result = target.getMessList();
            assertAll("result", () -> {
                assertThat(result, equalTo(responseEntityMock));
                responseEntity.verify(() -> ResponseEntity.ok(anyList()));
                verify(customerServiceMock).getMessList();
            });
        }
    }

    @Test()
    void sendJoinRequestWhenAccepted() {
        CustomerController target = new CustomerController(customerServiceMock, calculatorServiceMock);
        doReturn(true).when(customerServiceMock).sendJoinRequest("customer_id1", "A");
        ResponseEntity<?> result = target.sendJoinRequest("customer_id1", "A");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("Request sent successfully!");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntity));
            verify(customerServiceMock).sendJoinRequest("customer_id1", "A");
        });
    }

    @Test()
    void sendJoinRequestWhenNotAccepted() {
        CustomerController target = new CustomerController(customerServiceMock, calculatorServiceMock);
        doReturn(false).when(customerServiceMock).sendJoinRequest("customer_id1", "A");
        ResponseEntity<?> result = target.sendJoinRequest("customer_id1", "A");
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.accepted();
        ResponseEntity<?> responseEntity = bodyBuilder.body("Request already sent");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntity));
            verify(customerServiceMock).sendJoinRequest("customer_id1", "A");
        });
    }

    @Test()
    void addReviewWhenAdded() throws Exception {

        CustomerController target = new CustomerController(customerServiceMock, calculatorServiceMock);
        AddReviewRequest addReviewRequest = new AddReviewRequest();
        addReviewRequest.setMess_owner_username("A");
        addReviewRequest.setCustomer_username("B");
        doReturn(true).when(customerServiceMock).addReview(addReviewRequest);
        ResponseEntity<?> result = target.addReview(addReviewRequest);
        ResponseEntity<String> responseEntity = ResponseEntity.ok("Review Added Successfully");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntity));
            verify(customerServiceMock).addReview(addReviewRequest);
        });
    }

    @Test()
    void addReviewWhenNotAdded() throws Exception {
        CustomerController target = new CustomerController(customerServiceMock, calculatorServiceMock);
        AddReviewRequest addReviewRequest = new AddReviewRequest();
        addReviewRequest.setMess_owner_username("A");
        addReviewRequest.setCustomer_username("B");
        doReturn(false).when(customerServiceMock).addReview(addReviewRequest);
        ResponseEntity<?> result = target.addReview(addReviewRequest);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.badRequest();
        ResponseEntity<?> responseEntity = bodyBuilder.body("Already added review!");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntity));
            verify(customerServiceMock).addReview(addReviewRequest);
        });
    }

    @Test()
    void getProfileTest() throws Exception {
        ResponseEntity<CustomerProfileResponse> responseEntityMock = mock(ResponseEntity.class);
        try (MockedStatic<ResponseEntity> responseEntity = mockStatic(ResponseEntity.class)) {
            responseEntity.when(() -> ResponseEntity.ok(customerProfileResponseMock)).thenReturn(responseEntityMock);
            CustomerController target = new CustomerController(customerServiceMock, calculatorServiceMock);
            doReturn(customerProfileResponseMock).when(customerServiceMock).getProfile("A");
            ResponseEntity<?> result = target.getProfile("A");
            assertAll("result", () -> {
                assertThat(result, equalTo(responseEntityMock));
                responseEntity.verify(() -> ResponseEntity.ok(customerProfileResponseMock), atLeast(1));
                verify(customerServiceMock).getProfile("A");
            });
        }
    }

    @Test()
    void getProfileWhenCaughtException() throws Exception {

        try (MockedStatic<ResponseEntity> responseEntity = mockStatic(ResponseEntity.class, CALLS_REAL_METHODS)) {
            RuntimeException runtimeException = new RuntimeException("B");
            responseEntity.when(() -> ResponseEntity.ok(customerProfileResponseMock)).thenThrow(runtimeException);
            CustomerController target = new CustomerController(customerServiceMock, calculatorServiceMock);
            doReturn(customerProfileResponseMock).when(customerServiceMock).getProfile("A");
            ResponseEntity<?> result = target.getProfile("A");
            ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.badRequest();
            ResponseEntity<?> responseEntity2 = bodyBuilder.body("B");
            assertAll("result", () -> {
                assertThat(result, equalTo(responseEntity2));
                responseEntity.verify(() -> ResponseEntity.ok(customerProfileResponseMock), atLeast(1));
                verify(customerServiceMock, atLeast(1)).getProfile("A");
            });
        }
    }

    @Test()
    void resetPasswordWhenStatus() {

        CustomerController target = new CustomerController(customerServiceMock, calculatorServiceMock);
        doReturn(true).when(customerServiceMock).resetPassword(forgetPasswordRequestMock);
        ResponseEntity<?> result = target.resetPassword(forgetPasswordRequestMock);
        ResponseEntity<String> responseEntity = ResponseEntity.ok("Password Reset was successfull");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntity));
            verify(customerServiceMock).resetPassword(forgetPasswordRequestMock);
        });
    }

    @Test()
    void resetPasswordWhenNotStatus() {
        CustomerController target = new CustomerController(customerServiceMock, calculatorServiceMock);
        doReturn(false).when(customerServiceMock).resetPassword(forgetPasswordRequestMock);

        ResponseEntity<?> result = target.resetPassword(forgetPasswordRequestMock);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.badRequest();
        ResponseEntity<?> responseEntity = bodyBuilder.body("Unable to change the password");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntity));
            verify(customerServiceMock).resetPassword(forgetPasswordRequestMock);
        });
    }

    @Test()
    void calculateAmounntTest() throws Exception {
        CustomerController target = new CustomerController(customerServiceMock, calculatorServiceMock);
        Date date = new Date(23, 11, 1);
        Date date2 = new Date(23, 11, 4);
        doReturn(Float.parseFloat("200.0")).when(calculatorServiceMock).calculateAmount("messId1", date, date2, "id1");
        AmountRequest amountRequest = new AmountRequest();
        amountRequest.setEndDate(date2);
        amountRequest.setMessId("messId1");
        amountRequest.setId("id1");
        amountRequest.setStartDate(date);
        ResponseEntity<?> result = target.calculateAmounnt(amountRequest);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        ResponseEntity<?> responseEntity = bodyBuilder.body(Float.parseFloat("200.0"));

        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntity));
            verify(calculatorServiceMock).calculateAmount("messId1", date, date2, "id1");
        });
    }
}
