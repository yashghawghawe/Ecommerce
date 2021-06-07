package com.yash.shopping.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.shopping.dto.ProductRequestDTO;
import com.yash.shopping.dto.ProductResponseDTO;
import com.yash.shopping.exception.ProductNotFoundException;
import com.yash.shopping.service.ProductService;

/**
 * @author yash.ghawghawe
 *
 */
@RestController
@RequestMapping("/products")
public class ProductController {

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ProductService productService;

	/**
	 * @param productRequestDTO
	 * @return ResponseEntity<ProductResponseDTO>
	 * @throws ProductNotFoundException
	 * @throws CategoryNotFoundException
	 */
	@PostMapping
	public ResponseEntity<ProductResponseDTO> searchProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO)
			throws ProductNotFoundException {
		ProductResponseDTO productResponseDTO = productService.searchProduct(productRequestDTO);
		logger.info("products fetched successfully!");
		return ResponseEntity.ok(productResponseDTO);
	}

}
