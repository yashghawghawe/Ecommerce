package com.yash.shopping.service;

import java.util.List;

import com.yash.shopping.dto.OrderHistoryDTO;
import com.yash.shopping.exception.InvalidUserIdException;

/**
 * @author yash.ghawghawe
 *
 */
public interface OrderHistoryService {

	/**
	 * @param userId
	 * @return
	 * @throws InvalidUserIdException
	 */
	List<OrderHistoryDTO> myOrders(long userId) throws InvalidUserIdException;

}
