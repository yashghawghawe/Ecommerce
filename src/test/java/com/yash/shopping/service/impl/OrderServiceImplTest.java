package com.yash.shopping.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import feign.FeignException;

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
	private static TransactionDTO transactionDTO;
	private static Product product;

	@BeforeAll
	public static void setUp() {
		LocalDateTime localDateTime = LocalDateTime.now();
		orders = new ArrayList<>();
		order = new Order(order -> {
			order.setOrderId(1);
			order.setUserId(1);
			order.setTotalprice(10000);
			order.setDateTime(localDateTime);
			order.setOrderDetails(orderDetailsList);
		});

		orderDetailsList = new ArrayList<>();
		OrderDetails orderDetails = new OrderDetails(orderDetail -> {
			orderDetail.setPrice(10000);
			orderDetail.setProductname("redmi note 9");
			orderDetail.setQuantity(1);
			orderDetail.setUserId(1);
			orderDetail.setProduct(new Product());
		});
		orderDetailsList.add(orderDetails);
		order.setOrderDetails(orderDetailsList);

		orders.add(order);

		orderRequestDTO = new OrderRequestDTO();
		orderRequestDTO.setUserId("1");
		orderRequestDTO.setAccountNumber("12345678");

		List<OrderProductRequestDTO> orderProductRequestDTOs = new ArrayList<>();
		OrderProductRequestDTO orderProductRequestDTO = new OrderProductRequestDTO(orderProductRequest -> {
			orderProductRequest.setProductId("1");
			orderProductRequest.setQuantity("1");
		});
		orderProductRequestDTOs.add(orderProductRequestDTO);
		orderRequestDTO.setOrderProductRequestDTO(orderProductRequestDTOs);

		transactionDTO = new TransactionDTO("12345678", "11223344", 10000);

		product = new Product(product -> {
			product.setProductName("Redmi Note 9");
			//product.setCategoryId(1);
			product.setQuantity(100);
			product.setAmount(10000);
		});
	}

	// @Disabled

	/**
	 * @throws ProductNotFoundException
	 */
	@Test
	@DisplayName("Negative  Scenario: buy Products")
	public void buyProductFailedTest() throws ProductNotFoundException {
		// given
		when(productRepository.findById((long) 100)).thenReturn(null);

		// event or when
		Assertions.assertThrows(ProductNotFoundException.class, () -> orderServiceImpl.orderProducts(orderRequestDTO));
	}

	/**
	 * @throws TransactionFailedException
	 */
	@Test
	@Disabled
	@DisplayName("Negative  Scenario: TransactionFailedException")
	public void buyProductTransactionFailedTest() throws TransactionFailedException {
		// given
		when(productRepository.findById((long) 1)).thenReturn(Optional.of(product));
		when(productRepository.updateQuantity(99, 1)).thenReturn(1);
		when(bankClient.transferFund(transactionDTO)).thenThrow(TransactionFailedException.class);

		// event or when
		Assertions.assertThrows(FeignException.class, () -> orderServiceImpl.orderProducts(orderRequestDTO));
	}

	/**
	 * @throws ProductNotFoundException
	 * @throws TransactionFailedException
	 * @throws InsufficientBalanceException
	 */
	@Test
	@DisplayName("Positive scenario : buy products")
	public void buyProductTestFailedForProductNotFoundException()
			throws ProductNotFoundException, TransactionFailedException, InsufficientBalanceException {
		// given
		when(productRepository.findById((long) 1)).thenReturn(Optional.of(product));
		when(productRepository.updateQuantity(99, 1)).thenReturn(1);
		when(bankClient.transferFund(transactionDTO)).thenReturn("Successful Transaction");
		when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
		when(orderRepository.findById((long) 1)).thenReturn(Optional.of(order));

		// event or when
		OrderResponseDTO orderResponseDTO = orderServiceImpl.orderProducts(orderRequestDTO);

		// then or outcome
		verify(orderRepository).save(Mockito.any(Order.class));
		assertNotNull(orderResponseDTO);
	}

}
