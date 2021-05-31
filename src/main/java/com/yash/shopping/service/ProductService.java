package com.yash.shopping.service;

import javax.validation.Valid;

import com.yash.shopping.dto.ProductRequestDTO;
import com.yash.shopping.dto.ProductResponseDTO;
import com.yash.shopping.exception.CategoryNotFoundException;
import com.yash.shopping.exception.ProductNotFoundException;

/**
 * @author yash.ghawghawe
 *
 */
public interface ProductService {

	/**
	 * @param productRequestDTO
	 * @return ProductResponseDTO
	 * @throws ProductNotFoundException
	 * @throws CategoryNotFoundException
	 */
	ProductResponseDTO searchProduct(@Valid ProductRequestDTO productRequestDTO)
			throws ProductNotFoundException, CategoryNotFoundException;

}
