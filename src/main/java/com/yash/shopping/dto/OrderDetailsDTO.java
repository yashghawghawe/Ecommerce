package com.yash.shopping.dto;

import com.yash.shopping.entity.OrderDetails;

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
public class OrderDetailsDTO {

	private double price;
	private String productname;
	private int quantity;

	public OrderDetailsDTO(OrderDetails orderDetails) {
		this.price = orderDetails.getPrice();
		this.productname = orderDetails.getProductname();
		this.quantity = orderDetails.getQuantity();
	}

}
