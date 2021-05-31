package com.yash.shopping.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yash.shopping.dto.OrderDetailsDTO;
import com.yash.shopping.dto.OrderHistoryDTO;
import com.yash.shopping.exception.InvalidUserIdException;
import com.yash.shopping.service.OrderHistoryService;

/**
 * @author yash.ghawghawe
 *
 */
@SpringBootTest
public class OrderHistoryControllerTest {

	@Mock
	private OrderHistoryService orderHistoryService;

	@InjectMocks
	private OrderHistoryController orderHistoryController;

	private static List<OrderHistoryDTO> orderHistoryDTO;
	private static List<OrderDetailsDTO> orderDetailsDTO;

	@BeforeAll
	public static void setUp() {
		orderHistoryDTO = new ArrayList<>();
		OrderHistoryDTO historyDTO = new OrderHistoryDTO();
		historyDTO.setUserId(1);
		historyDTO.setOrderId(1);
		historyDTO.setTotalprice(10000.0);
		historyDTO.setDateTime(LocalDateTime.now());

		orderDetailsDTO = new ArrayList<>();
		OrderDetailsDTO orderDetailDTO = new OrderDetailsDTO();
		orderDetailDTO.setPrice(10000);
		orderDetailDTO.setProductname("Redmi Note 9");
		orderDetailDTO.setQuantity(1);
		orderDetailsDTO.add(orderDetailDTO);
		historyDTO.setOrderDetails(orderDetailsDTO);
		orderHistoryDTO.add(historyDTO);

	}

	/**
	 * @throws InvalidUserIdException
	 */
	@Test
	@DisplayName("Positive Scenario : fetching orders based on userid")
	public void myOrdersTest() throws InvalidUserIdException {
		// given
		when(orderHistoryService.myOrders(1)).thenReturn(orderHistoryDTO);

		// event or when
		ResponseEntity<List<OrderHistoryDTO>> orderHistory = orderHistoryController.myOrders(1);

		// then or outcome
		verify(orderHistoryService).myOrders(1);
		Assertions.assertNotNull(orderHistory);
		Assertions.assertEquals(HttpStatus.OK, orderHistory.getStatusCode());
	}

	/**
	 * @throws InvalidUserIdException
	 */
	@Test
	@DisplayName("Negative Scenario :  orders not fetched because of wrong userId")
	public void myOrdersFailedTest() throws InvalidUserIdException {
		// given
		when(orderHistoryService.myOrders(1)).thenThrow(InvalidUserIdException.class);

		// then or outcome
		Assertions.assertThrows(InvalidUserIdException.class, () -> orderHistoryController.myOrders(1));

	}

}
