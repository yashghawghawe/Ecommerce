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
import com.yash.shopping.dto.OrderRequestDTO;
import com.yash.shopping.dto.OrderResponseDTO;
import com.yash.shopping.exception.InsufficientBalanceException;
import com.yash.shopping.exception.ProductNotFoundException;
import com.yash.shopping.exception.TransactionFailedException;
import com.yash.shopping.service.OrderService;

/**
 * @author yash.ghawghawe
 *
 */
@SpringBootTest
public class OrderControllerTest {

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	private static OrderRequestDTO orderRequestDTO;
	private static OrderResponseDTO orderResponseDTO;
	private static List<OrderDetailsDTO> orderDetailsDTO;

	@BeforeAll
	public static void setUp() {
		orderRequestDTO = new OrderRequestDTO();
		orderRequestDTO.setUserId("1");
		orderRequestDTO.setAccountNumber("12345678");

		orderResponseDTO = new OrderResponseDTO();
		orderResponseDTO.setUserId(1);
		orderResponseDTO.setOrderId(1);
		orderResponseDTO.setTotalprice(10000.0);
		orderResponseDTO.setDate(LocalDateTime.now());
		orderResponseDTO.setMessage("Order Placed Successfully!");

		orderDetailsDTO = new ArrayList<>();
		OrderDetailsDTO orderDetailDTO = new OrderDetailsDTO();
		orderDetailDTO.setPrice(10000);
		orderDetailDTO.setProductname("Redmi Note 9");
		orderDetailDTO.setQuantity(1);
		orderDetailsDTO.add(orderDetailDTO);

		orderResponseDTO.setOrderDetails(orderDetailsDTO);
	}

	/**
	 * @throws TransactionFailedException
	 * @throws ProductNotFoundException
	 * @throws InsufficientBalanceException
	 */
	@Test
	@DisplayName("Positive Scenario : Buy Products")
	public void buyProducts()
			throws TransactionFailedException, ProductNotFoundException, InsufficientBalanceException {
		// given
		when(orderService.orderProducts(orderRequestDTO)).thenReturn(orderResponseDTO);

		// event or when
		ResponseEntity<OrderResponseDTO> response = orderController.orderProducts(orderRequestDTO);

		// then or outcome
		verify(orderService).orderProducts(orderRequestDTO);
		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@DisplayName("Neagtive Scenario : Buy Products")
	public void buyProductsFailed()
			throws TransactionFailedException, ProductNotFoundException, InsufficientBalanceException {
		// given
		when(orderService.orderProducts(orderRequestDTO)).thenThrow(TransactionFailedException.class);

		// then or outcome
		Assertions.assertThrows(TransactionFailedException.class, () -> orderController.orderProducts(orderRequestDTO));
	}

}
