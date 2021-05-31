package com.yash.shopping.service;

import javax.validation.Valid;

import com.yash.shopping.dto.OrderRequestDTO;
import com.yash.shopping.dto.OrderResponseDTO;
import com.yash.shopping.exception.InsufficientBalanceException;
import com.yash.shopping.exception.ProductNotFoundException;
import com.yash.shopping.exception.TransactionFailedException;

/**
 * @author yash.ghawghawe
 *
 */
public interface OrderService {

	/**
	 * @param orderRequestDTO
	 * @return OrderResponseDTO
	 * @throws TransactionFailedException
	 * @throws ProductNotFoundException 
	 * @throws InsufficientBalanceException 
	 */
	OrderResponseDTO orderProducts(@Valid OrderRequestDTO orderRequestDTO) throws TransactionFailedException, ProductNotFoundException, InsufficientBalanceException;

}
