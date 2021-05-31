package com.yash.shopping.service.impl;

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

import com.yash.shopping.dto.OrderHistoryDTO;
import com.yash.shopping.entity.Order;
import com.yash.shopping.entity.OrderDetails;
import com.yash.shopping.entity.Product;
import com.yash.shopping.exception.InvalidUserIdException;
import com.yash.shopping.repository.OrderRepository;

/**
 * @author yash.ghawghawe
 *
 */
@SpringBootTest
public class OrderHistoryServiceImplTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderHistoryServiceImpl orderHistoryServiceImpl;

	private static List<Order> orders;
	private static List<OrderDetails> orderDetailsList;

	@BeforeAll
	public static void setUp() {
		orders = new ArrayList<>();
		Order order = new Order();
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
	}

	/**
	 * @throws InvalidUserIdException
	 */
	@Test
	@DisplayName("Positive Scenario : fetching orders based on userid")
	public void myOrdersTest() throws InvalidUserIdException {
		// given
		when(orderRepository.findByUserId(1)).thenReturn(orders);

		// event or when
		List<OrderHistoryDTO> historyDTO = orderHistoryServiceImpl.myOrders(1);

		// then or outcome
		verify(orderRepository).findByUserId(1);
		Assertions.assertNotNull(historyDTO);

	}

	/**
	 * @throws InvalidUserIdException
	 */
	@Test
	@DisplayName("Negative Scenario :  orders not fetched because of wrong userId")
	public void myOrdersFailedTest() throws InvalidUserIdException {
		// given
		when(orderRepository.findByUserId(2)).thenReturn(null);

		// then or outcome
		Assertions.assertThrows(InvalidUserIdException.class, () -> orderHistoryServiceImpl.myOrders(2));

	}

}
