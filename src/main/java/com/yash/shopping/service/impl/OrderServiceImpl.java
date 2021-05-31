package com.yash.shopping.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yash.shopping.dto.OrderDetailsDTO;
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
import com.yash.shopping.service.OrderService;

import feign.FeignException;

/**
 * @author yash.ghawghawe
 *
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BankClient bankClient;

	//@Value("${fastkart.accountNo}")
	private String beneficiaryAccountNumber="11223344";

	/**
	 * @param orderRequestDTO
	 * @return OrderResponseDTO
	 * @throws TransactionFailedException
	 * @throws ProductNotFoundException
	 * @throws InsufficientBalanceException
	 */
	@Override
	public OrderResponseDTO orderProducts(@Valid OrderRequestDTO orderRequestDTO)
			throws TransactionFailedException, ProductNotFoundException, InsufficientBalanceException {
		double totalamount = 0;
		Order order = new Order();
		List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		List<OrderProductRequestDTO> orderProductRequestDTOs = orderRequestDTO.getOrderProductRequestDTO();
		order.setUserId(Long.parseLong(orderRequestDTO.getUserId()));
		order.setDateTime(LocalDateTime.now());
		totalamount = saveOrderDetails(orderRequestDTO, totalamount, orderDetailsList, orderProductRequestDTOs);

		try {
			bankClient.transferFund(
					new TransactionDTO(orderRequestDTO.getAccountNumber(), beneficiaryAccountNumber, totalamount));
		} catch (FeignException e) {
			logger.info("Transaction failed");
			if (e.getMessage().contains("TransactionFailedException")) {
				throw new TransactionFailedException("Transaction Failed");
			}
			if (e.getMessage().contains("InsufficientBalanceException")) {
				throw new InsufficientBalanceException("balance is low for purchase");
			}

		}

		order.setTotalprice(totalamount);
		order.setOrderDetails(orderDetailsList);
		Order savedOrder = orderRepository.save(order);
		logger.info("Order has been placed successfully!");
		return getResponseDTO(savedOrder);
	}

	/**
	 * @param orderRequestDTO
	 * @param totalamount
	 * @param orderDetailsList
	 * @param orderProductRequestDTOs
	 * @return double
	 * @throws ProductNotFoundException
	 */
	private double saveOrderDetails(OrderRequestDTO orderRequestDTO, double totalamount,
			List<OrderDetails> orderDetailsList, List<OrderProductRequestDTO> orderProductRequestDTOs)
			throws ProductNotFoundException {
		for (OrderProductRequestDTO orderProductRequestDTO : orderProductRequestDTOs) {
			OrderDetails orderDetails = new OrderDetails();
			Optional<Product> products = productRepository
					.findById(Long.parseLong(orderProductRequestDTO.getProductId()));
			if (ObjectUtils.isEmpty(products)) {
				throw new ProductNotFoundException("product not found");
			}
			if (products.isPresent()) {
				Product product = products.get();
				double price = product.getAmount() * Double.parseDouble(orderProductRequestDTO.getQuantity());
				int updatedQuantity = product.getQuantity() - Integer.parseInt(orderProductRequestDTO.getQuantity());
				productRepository.updateQuantity(updatedQuantity, product.getProductId());
				totalamount = totalamount + price;
				orderDetails.setPrice(price);
				orderDetails.setProduct(product);
				orderDetails.setProductname(product.getProductName());
				orderDetails.setQuantity(Integer.parseInt(orderProductRequestDTO.getQuantity()));
				orderDetails.setUserId(Long.parseLong(orderRequestDTO.getUserId()));
				orderDetailsList.add(orderDetails);
			}
		}
		return totalamount;
	}

	/**
	 * @param savedOrder
	 * @return OrderResponseDTO
	 */
	private OrderResponseDTO getResponseDTO(Order savedOrder) {
		Order savedOrders = new Order();
		Optional<Order> orders = orderRepository.findById(savedOrder.getOrderId());
		if (orders.isPresent()) {
			savedOrders = orders.get();
		}

		List<OrderDetails> orderDetails = savedOrders.getOrderDetails();
		List<OrderDetailsDTO> orderDetailsDTOs = new ArrayList<>();
		orderDetails.forEach(orderDetail -> {
			OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
			BeanUtils.copyProperties(orderDetail, orderDetailsDTO);
			orderDetailsDTOs.add(orderDetailsDTO);
		});
		return new OrderResponseDTO("Order has been booked successfully", savedOrder.getOrderId(),
				savedOrder.getUserId(), savedOrder.getDateTime(), savedOrders.getTotalprice(), orderDetailsDTOs);
	}

}
