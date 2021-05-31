package com.yash.shopping.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yash.shopping.dto.LoginDTO;
import com.yash.shopping.dto.MessageResposne;
import com.yash.shopping.exception.InvalidCredentialsException;
import com.yash.shopping.service.LoginService;

/**
 * @author yash.ghawghawe
 *
 */
@SpringBootTest
public class LoginControllerTest {

	@Mock
	private LoginService loginService;

	@InjectMocks
	private LoginController loginController;

	private static LoginDTO loginDTO;

	@BeforeAll
	public static void setUp() {
		loginDTO = new LoginDTO();
		loginDTO.setPassword("yash");
		loginDTO.setUsername("yash ghawghawe");
	}

	/**
	 * @throws InvalidCredentialsException
	 */
	@Test
	@DisplayName("Positive Scenario : user loogged in successfully")
	public void testAuthenticate() throws InvalidCredentialsException {
		// given
		when(loginService.authenticate(loginDTO)).thenReturn(true);

		// event or when
		ResponseEntity<MessageResposne> message = loginController.login(loginDTO);

		// then or outcome
		verify(loginService).authenticate(loginDTO);
		Assertions.assertNotNull(message.getBody());
		Assertions.assertEquals(HttpStatus.OK, message.getStatusCode());

	}

	/**
	 * @throws InvalidCredentialsException
	 */
	@Test
	@DisplayName("Negative Scenario : wrong Credentails")
	public void testAuthenticateFailed() throws InvalidCredentialsException {
		// given
		when(loginService.authenticate(loginDTO)).thenThrow(InvalidCredentialsException.class);

		// then or outcome
		Assertions.assertThrows(InvalidCredentialsException.class, () -> loginController.login(loginDTO));

	}

}
