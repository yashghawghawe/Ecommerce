package com.yash.shopping.dto;

import java.util.function.Consumer;

import com.yash.shopping.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yash.ghawghawe
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private long productId;
	private String productName;
	private double amount;
	private int quantity;

	public ProductDTO(Product product) {
		this.productId = product.getProductId();
		this.productName = product.getProductName();
		this.amount = product.getAmount();
		this.quantity = product.getQuantity();
	}
	
	public ProductDTO(Consumer<ProductDTO> builder) {
		builder.accept(this);
	}

}
