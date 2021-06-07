package com.yash.shopping.dto;

import java.util.function.Consumer;

import javax.validation.constraints.NotEmpty;

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
public class LoginDTO {

	@NotEmpty(message = "Username cannot be empty")
	private String username;
	@NotEmpty(message = "Password cannot be empty")
	private String password;

	public LoginDTO(Consumer<LoginDTO> builder) {
		builder.accept(this);
	}

}
