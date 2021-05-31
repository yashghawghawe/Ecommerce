package com.yash.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yash.shopping.entity.Category;

/**
 * @author yash.ghawghawe
 *
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	/**
	 * @param categoryName
	 * @return Category
	 */
	Category findByCategoryName(String categoryName);


}
