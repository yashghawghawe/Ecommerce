package com.yash.shopping.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.shopping.dto.LoginDTO;
import com.yash.shopping.dto.MessageResposne;
import com.yash.shopping.exception.InvalidCredentialsException;
import com.yash.shopping.service.LoginService;

/**
 * @author yash.ghawghawe
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginService service;

	/**
	 * @param loginDTO
	 * @return
	 * @throws InvalidCredentialsException 
	 */
	@PostMapping
	public ResponseEntity<MessageResposne> login(@Valid @RequestBody LoginDTO loginDTO) throws InvalidCredentialsException  {
		service.authenticate(loginDTO);
		logger.info("Logged in Successfully");
		return ResponseEntity.ok(new MessageResposne("Logged in Successfully"));
	}

}
