package com.yash.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yash.shopping.entity.Product;

/**
 * @author yash.ghawghawe
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * @param productName
	 * @return List<Product>
	 */
	List<Product> findByProductNameContains(String productName);

	/**
	 * @param categoryId
	 * @return List<Product>
	 */
	//List<Product> findByCategoryId(long categoryId);

	/**
	 * @param quantity
	 * @param productId
	 * @return int
	 */
	@Modifying
	@Query("update Product p set p.quantity =:quantity where p.productId = :productId")
	int updateQuantity(@Param("quantity") int quantity,@Param("productId") long productId);

}
