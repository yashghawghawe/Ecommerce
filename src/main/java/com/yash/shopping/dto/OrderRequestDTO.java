package com.yash.shopping.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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
public class OrderRequestDTO {

	@NotEmpty(message = "userId cannot be empty")
	@Pattern(regexp = "^[0-9]*$", message = "must be a number")
	private String userId;
	@NotEmpty(message = "accountnumber cannot be empty")
	@Pattern(regexp = "^[0-9]*$", message = "must be a number")
	private String accountNumber;
	@Valid
	private List<OrderProductRequestDTO> orderProductRequestDTO;

}
