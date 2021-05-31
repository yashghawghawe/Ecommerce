package com.yash.shopping.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yash.shopping.dto.ProductDTO;
import com.yash.shopping.dto.ProductRequestDTO;
import com.yash.shopping.dto.ProductResponseDTO;
import com.yash.shopping.entity.Category;
import com.yash.shopping.entity.Product;
import com.yash.shopping.exception.CategoryNotFoundException;
import com.yash.shopping.exception.ProductNotFoundException;
import com.yash.shopping.repository.CategoryRepository;
import com.yash.shopping.repository.ProductRepository;
import com.yash.shopping.service.ProductService;

/**
 * @author yash.ghawghawe
 *
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * @see com.yash.shopping.service.ProductService#searchProduct(com.yash.shopping.dto.ProductRequestDTO)
	 */
	@Override
	public ProductResponseDTO searchProduct(@Valid ProductRequestDTO productRequestDTO)
			throws ProductNotFoundException, CategoryNotFoundException {
		List<Product> products = productRepository.findByProductNameContains(productRequestDTO.getProductName());
		if (ObjectUtils.isEmpty(products)) {
			Category category = categoryRepository.findByCategoryName(productRequestDTO.getCategoryName());
			if (ObjectUtils.isEmpty(category)) {
				logger.info("product Not Found");
				throw new ProductNotFoundException("product not found");
			}
			if (ObjectUtils.isEmpty(category) && ObjectUtils.isEmpty(products)) {
				logger.info("product Not Found");
				throw new ProductNotFoundException("product not found");
			}
			products = productRepository.findByCategoryId(category.getCategoryId());
		}
		ProductResponseDTO productResponseDTO = new ProductResponseDTO();
		List<ProductDTO> productDTOs = new ArrayList<>();
		products.forEach(product -> {
			ProductDTO productDTO = new ProductDTO();
			BeanUtils.copyProperties(product, productDTO);
			productDTOs.add(productDTO);
		});
		productResponseDTO.setProductDTOs(productDTOs);
		return productResponseDTO;

	}
}
