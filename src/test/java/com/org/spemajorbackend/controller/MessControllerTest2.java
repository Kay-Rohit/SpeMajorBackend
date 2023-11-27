package com.org.spemajorbackend.controller;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;

import java.util.List;

import com.org.spemajorbackend.dto.AmountBalancesResponse;
import com.org.spemajorbackend.dto.JoinRequestResponse;
import com.org.spemajorbackend.dro.UpdateMessDetails;
import com.org.spemajorbackend.service.CalculatorService;
import com.org.spemajorbackend.service.MessService;
import org.springframework.http.ResponseEntity;
import org.mockito.MockedStatic;

import java.util.ArrayList;

import com.org.spemajorbackend.dro.AddMenuRequest;

import static org.mockito.ArgumentMatchers.anyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.doReturn;

@Timeout(value = 5)
class MessControllerTest2 {
    private final MessService messServiceMock = mock(MessService.class, "messService");
    private final CalculatorService calculatorServiceMock = mock(CalculatorService.class, "calculatorService");
    private final AmountBalancesResponse amountBalancesResponseMock = mock(AmountBalancesResponse.class);
    private final ResponseEntity.BodyBuilder responseEntityBodyBuilderMock = mock(ResponseEntity.BodyBuilder.class);
    private final ResponseEntity<?> responseEntityMock = mock(ResponseEntity.class);
    private final UpdateMessDetails updateMessDetailsMock = mock(UpdateMessDetails.class);

    @Test()
    void addMenuWhenAdded() {
        MessController target = new MessController(messServiceMock, calculatorServiceMock);
        List<AddMenuRequest> addMenuRequestList = new ArrayList<>();
        doReturn(true).when(messServiceMock).addMenuItems(addMenuRequestList, "A");
        ResponseEntity<?> result = target.addMenu(addMenuRequestList, "A");
        ResponseEntity<String> responseEntity = ResponseEntity.ok("Added Successfully");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntity));
            verify(messServiceMock).addMenuItems(addMenuRequestList, "A");
        });
    }

    @Test()
    void addMenuWhenNotAdded() {
        try (MockedStatic<ResponseEntity> responseEntity = mockStatic(ResponseEntity.class)) {
            responseEntity.when(() -> ResponseEntity.unprocessableEntity()).thenReturn(responseEntityBodyBuilderMock);
            doReturn(responseEntityMock).when(responseEntityBodyBuilderMock).build();
            MessController target = new MessController(messServiceMock, calculatorServiceMock);
            List<AddMenuRequest> addMenuRequestList = new ArrayList<>();
            doReturn(false).when(messServiceMock).addMenuItems(addMenuRequestList, "A");
            ResponseEntity<?> result = target.addMenu(addMenuRequestList, "A");

            assertAll("result", () -> {
                assertThat(result, equalTo(responseEntityMock));
                responseEntity.verify(() -> ResponseEntity.unprocessableEntity(), atLeast(1));
                verify(responseEntityBodyBuilderMock).build();
                verify(messServiceMock).addMenuItems(addMenuRequestList, "A");
            });
        }
    }

    @Test()
    void acceptRequestTest() {
        MessController target = new MessController(messServiceMock, calculatorServiceMock);
        doReturn(responseEntityMock).when(messServiceMock).acceptRequest("owner_id1", "A");
        ResponseEntity<?> result = target.acceptRequest("owner_id1", "A");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntityMock));
            verify(messServiceMock).acceptRequest("owner_id1", "A");
        });
    }

    @Test()
    void rejectRequestTest() {
        MessController target = new MessController(messServiceMock, calculatorServiceMock);
        doReturn(responseEntityMock).when(messServiceMock).rejectRequest("owner_id1", "A");
        ResponseEntity<?> result = target.rejectRequest("owner_id1", "A");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntityMock));
            verify(messServiceMock).rejectRequest("owner_id1", "A");
        });
    }

    @Test()
    void sendOwnerDetailsWhenDetailsNotEqualsNull() throws Exception {
        MessController target = new MessController(messServiceMock, calculatorServiceMock);
        doReturn(responseEntityMock).when(messServiceMock).getOwnerDetails("owner_id1");
        ResponseEntity<?> result = target.sendOwnerDetails("owner_id1");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntityMock));
            verify(messServiceMock).getOwnerDetails("owner_id1");
        });
    }

    @Test()
    void updateOwnerDetailsTest() throws Exception {
        //Arrange Statement(s)
        MessController target = new MessController(messServiceMock, calculatorServiceMock);
        doReturn(responseEntityMock).when(messServiceMock).updateOwnerDetails("owner_id1", updateMessDetailsMock);
        //Act Statement(s)
        ResponseEntity<?> result = target.updateOwnerDetails("owner_id1", updateMessDetailsMock);
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntityMock));
            verify(messServiceMock).updateOwnerDetails("owner_id1", updateMessDetailsMock);
        });
    }

    @Test()
    void updateOwnerDetailsWhenCaughtException() throws Exception {
        MessController target = new MessController(messServiceMock, calculatorServiceMock);
        RuntimeException runtimeException = new RuntimeException("A");
        doThrow(runtimeException).when(messServiceMock).updateOwnerDetails("owner_id1", updateMessDetailsMock);
        ResponseEntity<?> result = target.updateOwnerDetails("owner_id1", updateMessDetailsMock);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.badRequest();
        ResponseEntity<?> responseEntity = bodyBuilder.body("A");
        assertAll("result", () -> {
            assertThat(result, equalTo(responseEntity));
            verify(messServiceMock).updateOwnerDetails("owner_id1", updateMessDetailsMock);
        });
    }

    @Test()
    void seeJoiningRequestsTest() {
        ResponseEntity<List> responseEntityMock = mock(ResponseEntity.class);
        try (MockedStatic<ResponseEntity> responseEntity = mockStatic(ResponseEntity.class)) {
            responseEntity.when(() -> ResponseEntity.ok(anyList())).thenReturn(responseEntityMock);
            MessController target = new MessController(messServiceMock, calculatorServiceMock);
            List<JoinRequestResponse> joinRequestResponseList = new ArrayList<>();
            doReturn(joinRequestResponseList).when(messServiceMock).seeJoiningRequests("owner_id1");
            ResponseEntity<?> result = target.seeJoiningRequests("owner_id1");
            assertAll("result", () -> {
                assertThat(result, equalTo(responseEntityMock));
                responseEntity.verify(() -> ResponseEntity.ok(anyList()));
                verify(messServiceMock).seeJoiningRequests("owner_id1");
            });
        }
    }

    @Test()
    void amountBalanceTest() throws Exception {
        ResponseEntity<AmountBalancesResponse> responseEntityMock = mock(ResponseEntity.class);
        try (MockedStatic<ResponseEntity> responseEntity = mockStatic(ResponseEntity.class)) {
            responseEntity.when(() -> ResponseEntity.ok()).thenReturn(responseEntityBodyBuilderMock);
            doReturn(responseEntityMock).when(responseEntityBodyBuilderMock).body(amountBalancesResponseMock);
            MessController target = new MessController(messServiceMock, calculatorServiceMock);
            doReturn(amountBalancesResponseMock).when(calculatorServiceMock).calculateTotal("owner_id1");
            ResponseEntity<AmountBalancesResponse> result = target.amountBalance("owner_id1");
            assertAll("result", () -> {
                assertThat(result, equalTo(responseEntityMock));
                responseEntity.verify(() -> ResponseEntity.ok(), atLeast(1));
                verify(responseEntityBodyBuilderMock).body(amountBalancesResponseMock);
                verify(calculatorServiceMock).calculateTotal("owner_id1");
            });
        }
    }
}
