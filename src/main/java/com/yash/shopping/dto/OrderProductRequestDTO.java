package com.yash.shopping.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class OrderProductRequestDTO {

	@NotEmpty(message = "productId cannot be empty")
	@Pattern(regexp = "^[0-9]*$", message = "must be a number")
	private String productId;
	@NotEmpty(message = "quantity cannot be empty")
	@Size(min = 1, max = 3)
	@Pattern(regexp = "^[0-9]*$", message = "must be a number")
	private String quantity;

}
