package com.yash.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yash.shopping.entity.OrderDetails;

/**
 * @author yash.ghawghawe
 *
 */
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long>{

	/**
	 * @param userId
	 * @return List<OrderDetails>
	 */
	List<OrderDetails> findByUserId(long userId);

}
