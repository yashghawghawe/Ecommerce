package com.yash.shopping.dto;

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
public class ProductRequestDTO {

	@NotEmpty(message = "product name cannot be empty")
	private String productName;

	@Pattern(regexp = "^[a-zA-Z]*$", message = "Enter Alphabets only")
	private String categoryName;

}
