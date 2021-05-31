package com.yash.shopping.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.yash.shopping.dto.ProductDTO;
import com.yash.shopping.dto.ProductRequestDTO;
import com.yash.shopping.dto.ProductResponseDTO;
import com.yash.shopping.entity.Category;
import com.yash.shopping.entity.Product;
import com.yash.shopping.exception.CategoryNotFoundException;
import com.yash.shopping.exception.ProductNotFoundException;
import com.yash.shopping.repository.CategoryRepository;
import com.yash.shopping.repository.ProductRepository;

/**
 * @author yash.ghawghawe
 *
 */
@SpringBootTest
public class ProductServiceImplTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	private static List<Product> products;
	private static ProductRequestDTO productRequestDTO;
	private static ProductResponseDTO productResponseDTO;

	@BeforeAll
	public static void setUp() {
		products = new ArrayList<>();
		Product product = new Product();
		product.setProductName("redmi note 9");
		product.setAmount(10000);
		product.setQuantity(100);
		Category category = new Category();
		category.setCategoryName("mobiles");
		product.setCategoryId(1);
		products.add(product);

		productRequestDTO = new ProductRequestDTO();
		productRequestDTO.setCategoryName("Mobiles");
		productRequestDTO.setProductName("redmi note 9");

		List<ProductDTO> productDTOs = new ArrayList<>();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductName("redmi note 9");
		productDTO.setAmount(10000);
		productDTO.setQuantity(100);
		productDTO.setProductId(1);
		productDTOs.add(productDTO);

		productResponseDTO = new ProductResponseDTO();
		productResponseDTO.setProductDTOs(productDTOs);

	}

	/**
	 * @throws ProductNotFoundException
	 * @throws CategoryNotFoundException
	 */
	@Test
	@DisplayName("Postive Scenario : Fetching Products")
	public void searchProductTest() throws ProductNotFoundException, CategoryNotFoundException {
		// given
		when(productRepository.findByProductNameContains("redmi note 9")).thenReturn(products);

		// event or when
		ProductResponseDTO productResponseDTO = productServiceImpl.searchProduct(productRequestDTO);

		// then or outcome
		verify(productRepository).findByProductNameContains("redmi note 9");
		Assertions.assertNotNull(productResponseDTO);
	}

	@Test
	@DisplayName("negative Scenario : Fetching Products")
	public void searchProductFailedTest() throws ProductNotFoundException, CategoryNotFoundException {
		// given
		when(productRepository.findByProductNameContains("redmi note 9")).thenReturn(null);
		when(categoryRepository.findByCategoryName("mobile")).thenReturn(null);

		// then or outcome
		Assertions.assertThrows(ProductNotFoundException.class,
				() -> productServiceImpl.searchProduct(productRequestDTO));
	}

}
