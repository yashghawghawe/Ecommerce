package com.yash.shopping.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.yash.shopping.dto.LoginDTO;
import com.yash.shopping.entity.Address;
import com.yash.shopping.entity.User;
import com.yash.shopping.exception.InvalidCredentialsException;
import com.yash.shopping.repository.UserRepository;

@SpringBootTest
public class LoginServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private LoginServiceImpl loginService;

	private static LoginDTO loginDTO;
	private static User user;
	private static Address address;

	@BeforeAll
	public static void setUp() {
		loginDTO = new LoginDTO();
		loginDTO.setPassword("yash");
		loginDTO.setUsername("yash ghawghawe");

		address = new Address();
		address.setTown("RatanNagar");
		address.setState("Maharashtra");
		address.setCity("Nagpur");
		address.setPincode("440009");

		user = new User();
		user.setUsername("yash ghawghawe");
		user.setPassword("yash");
		user.setAge(new BigDecimal("23"));
		user.setContactNo("8983568315");
		user.setEmail("yash@gmail.com");
		user.setAddress(address);

	}

	@Test
	@DisplayName("Positive Scenario: User exists")
	public void testAuthenticate() throws org.apache.http.auth.InvalidCredentialsException {
		// given
		when(userRepository.findByUsernameAndPassword("yash ghawghawe", "yash")).thenReturn(user);

		// event or when
		boolean user = loginService.authenticate(loginDTO);

		// then or outcome
		verify(userRepository).findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
		Assertions.assertTrue(user);
	}

	@Test
	@DisplayName("Negative Scenario: User doesn`t exists")
	public void testAuthenticateFail() throws InvalidCredentialsException {
		// given
		when(userRepository.findByUsernameAndPassword("yash ghawghawe", "yash"))
				.thenReturn(null);

		// then or outcome
		loginDTO.setUsername("yash ghawghawe");
		loginDTO.setPassword("yash");
		Assertions.assertThrows(InvalidCredentialsException.class, () -> loginService.authenticate(loginDTO));
	}

}
