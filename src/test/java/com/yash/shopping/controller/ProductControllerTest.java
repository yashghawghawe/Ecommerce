package com.yash.shopping.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yash.shopping.dto.ProductDTO;
import com.yash.shopping.dto.ProductRequestDTO;
import com.yash.shopping.dto.ProductResponseDTO;
import com.yash.shopping.exception.ProductNotFoundException;
import com.yash.shopping.service.ProductService;

/**
 * @author yash.ghawghawe
 *
 */
@SpringBootTest
public class ProductControllerTest {

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	private static ProductRequestDTO productRequestDTO;
	private static ProductResponseDTO productResponseDTO;

	@BeforeAll
	public static void setUp() {
		productRequestDTO = new ProductRequestDTO();
		productRequestDTO.setCategoryName("Mobiles");
		productRequestDTO.setProductName("redmi note 9");

		List<ProductDTO> productDTOs = new ArrayList<>();
		ProductDTO productDTO = new ProductDTO(proDTO -> {
			proDTO.setProductName("redmi note 9");
			proDTO.setAmount(10000);
			proDTO.setQuantity(100);
			proDTO.setProductId(1);
		});

		productDTOs.add(productDTO);

		productResponseDTO = new ProductResponseDTO();
		productResponseDTO.setProductDTOs(productDTOs);
	}

	/**
	 * @throws ProductNotFoundException
	 * @throws CategoryNotFoundException
	 */
	@Test
	@DisplayName("Positive Scenario: Products Fetched Successfully")
	public void searchProductTest() throws ProductNotFoundException {
		// given
		when(productService.searchProduct(productRequestDTO)).thenReturn(productResponseDTO);

		// event or when
		ResponseEntity<ProductResponseDTO> response = productController.searchProduct(productRequestDTO);

		verify(productService).searchProduct(productRequestDTO);
		Assertions.assertEquals(productResponseDTO, response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	/**
	 * @throws ProductNotFoundException
	 * @throws CategoryNotFoundException
	 */
	@Test
	@DisplayName("Negative Scenario: Product not found")
	public void searchProductFailedTest() throws ProductNotFoundException {
		// given
		when(productService.searchProduct(productRequestDTO)).thenThrow(ProductNotFoundException.class);

		// outcome
		Assertions.assertThrows(ProductNotFoundException.class,
				() -> productController.searchProduct(productRequestDTO));
	}

}
