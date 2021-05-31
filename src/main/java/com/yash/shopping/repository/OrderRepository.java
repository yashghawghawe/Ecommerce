package com.yash.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.shopping.entity.Order;

/**
 * @author yash.ghawghawe
 *
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

	/**
	 * @param userId
	 * @return List<Order>
	 */
	List<Order> findByUserId(long userId);

}
