package com.yash.shopping.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yash.shopping.dto.OrderDetailsDTO;
import com.yash.shopping.dto.OrderHistoryDTO;
import com.yash.shopping.entity.Order;
import com.yash.shopping.exception.InvalidUserIdException;
import com.yash.shopping.repository.OrderRepository;
import com.yash.shopping.service.OrderHistoryService;

/**
 * @author yash.ghawghawe
 *
 */
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

	private final Logger logger = LoggerFactory.getLogger(OrderHistoryServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;

	/**
	 * @see com.yash.shopping.service.OrderHistoryService#myOrders(long)
	 */
	@Override
	public List<OrderHistoryDTO> myOrders(long userId) throws InvalidUserIdException {
		List<Order> orders = orderRepository.findByUserId(userId);
		if (ObjectUtils.isEmpty(orders)) {
			logger.info("Please enter correct userId : " + userId);
			throw new InvalidUserIdException("Please enter correct userId");
		}
		List<OrderHistoryDTO> orderHistoryDTOs = new ArrayList<>();
		orders.forEach(order -> {
			OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO();
			BeanUtils.copyProperties(order, orderHistoryDTO);

			List<OrderDetailsDTO> orderDetailsDTOs = new ArrayList<>();
			order.getOrderDetails().forEach(orderDetails -> {
				OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
				BeanUtils.copyProperties(orderDetails, orderDetailsDTO);
				orderDetailsDTO.setPrice(orderDetailsDTO.getPrice() / orderDetailsDTO.getQuantity());
				orderDetailsDTOs.add(orderDetailsDTO);
			});
			orderHistoryDTO.setOrderDetails(orderDetailsDTOs);
			orderHistoryDTOs.add(orderHistoryDTO);
		});
		logger.info("order history fetched for the userId : " + userId);
		return orderHistoryDTOs;
	}

}
