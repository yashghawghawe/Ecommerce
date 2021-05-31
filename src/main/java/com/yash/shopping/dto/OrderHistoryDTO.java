package com.yash.shopping.dto;

import java.time.LocalDateTime;
import java.util.List;

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
public class OrderHistoryDTO {

	private long userId;
	private long orderId;
	private LocalDateTime dateTime;
	private Double totalprice;
	private List<OrderDetailsDTO> orderDetails;

}
