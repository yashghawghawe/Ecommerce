package com.yash.shopping.service.impl;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.yash.shopping.dto.OrderProductRequestDTO;
import com.yash.shopping.dto.OrderRequestDTO;
import com.yash.shopping.dto.OrderResponseDTO;
import com.yash.shopping.dto.TransactionDTO;
import com.yash.shopping.entity.Order;
import com.yash.shopping.entity.OrderDetails;
import com.yash.shopping.entity.Product;
import com.yash.shopping.exception.InsufficientBalanceException;
import com.yash.shopping.exception.ProductNotFoundException;
import com.yash.shopping.exception.TransactionFailedException;
import com.yash.shopping.feign.BankClient;
import com.yash.shopping.repository.OrderRepository;
import com.yash.shopping.repository.ProductRepository;

/**
 * @author yash.ghawghawe
 *
 */
@SpringBootTest
public class OrderServiceImplTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private BankClient bankClient;

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;

	private static List<Order> orders;
	private static List<OrderDetails> orderDetailsList;
	private static OrderRequestDTO orderRequestDTO;
	private static Order order;

	@BeforeAll
	public static void setUp() {
		orders = new ArrayList<>();
		order = new Order();
		order.setOrderId(1);
		order.setUserId(1);
		order.setTotalprice(10000);
		order.setDateTime(LocalDateTime.now());

		orderDetailsList = new ArrayList<>();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setPrice(10000);
		orderDetails.setProductname("redmi note 9");
		orderDetails.setQuantity(1);
		orderDetails.setUserId(1);
		orderDetails.setProduct(new Product());
		orderDetailsList.add(orderDetails);
		order.setOrderDetails(orderDetailsList);

		orders.add(order);

		orderRequestDTO = new OrderRequestDTO();
		orderRequestDTO.setUserId("1");
		orderRequestDTO.setAccountNumber("12345678");
		List<OrderProductRequestDTO> orderProductRequestDTOs = new ArrayList<>();
		OrderProductRequestDTO orderProductRequestDTO = new OrderProductRequestDTO();
		orderProductRequestDTO.setProductId("1");
		orderProductRequestDTO.setQuantity("1");
		orderProductRequestDTOs.add(orderProductRequestDTO);
		orderRequestDTO.setOrderProductRequestDTO(orderProductRequestDTOs);
	}

	// @Disabled
	@Test
	@DisplayName("Negative Scenario:ProductNotFoundException")
	public void buyProductTestFailedForProductNotFoundException() throws ProductNotFoundException {
		// given
		when(productRepository.findById((long) 100)).thenReturn(null);

		// event or when
		Assertions.assertThrows(ProductNotFoundException.class, () -> orderServiceImpl.orderProducts(orderRequestDTO));
	}

}
