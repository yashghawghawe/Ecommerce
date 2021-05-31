package com.yash.shopping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * @param orderRequestDTO
	 * @return ResponseEntity<OrderResponseDTO> 
	 * @throws TransactionFailedException
	 * @throws ProductNotFoundException 
	 * @throws InsufficientBalanceException 
	 */
	@PostMapping
	public ResponseEntity<OrderResponseDTO> orderProducts(@RequestBody @Valid OrderRequestDTO orderRequestDTO)
			throws TransactionFailedException, ProductNotFoundException, InsufficientBalanceException {
		OrderResponseDTO orderResponseDTO = orderService.orderProducts(orderRequestDTO);
		return ResponseEntity.ok(orderResponseDTO);

	}
}
