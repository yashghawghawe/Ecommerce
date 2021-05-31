package com.yash.shopping.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yash.ghawghawe
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDTO {

	private String message;
	private long orderId;
	private long userId;
	private LocalDateTime date;
	private double totalprice;
	private List<OrderDetailsDTO> orderDetails;

	public OrderResponseDTO(String message, long orderId, long userId, LocalDateTime date, double totalprice,
			List<OrderDetailsDTO> orderDetails) {
		super();
		this.message = message;
		this.orderId = orderId;
		this.userId = userId;
		this.date = date;
		this.totalprice = totalprice;
		this.orderDetails = orderDetails;
	}

}
