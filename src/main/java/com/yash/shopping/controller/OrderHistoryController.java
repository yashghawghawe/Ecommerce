package com.yash.shopping.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.shopping.dto.OrderHistoryDTO;
import com.yash.shopping.exception.InvalidUserIdException;
import com.yash.shopping.service.OrderHistoryService;

/**
 * @author yash.ghawghawe
 *
 */
@RestController
@RequestMapping("/users")
public class OrderHistoryController {

	private final Logger logger = LoggerFactory.getLogger(OrderHistoryController.class);

	@Autowired
	private OrderHistoryService orderHistoryService;

	/**
	 * @param userId
	 * @return ResponseEntity<List<OrderHistoryDTO>>
	 * @throws InvalidUserIdException
	 */
	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<OrderHistoryDTO>> myOrders(
			@Valid @Pattern(regexp = "^[0-9]*$", message = "must be a number") @PathVariable("userId") long userId)
			throws InvalidUserIdException {
		List<OrderHistoryDTO> orderHistoryDTO = orderHistoryService.myOrders(userId);
		logger.info("order history retrieved for userId : " + userId);
		return ResponseEntity.ok(orderHistoryDTO);

	}

}
