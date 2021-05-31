package com.yash.shopping.service.impl;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yash.shopping.dto.LoginDTO;
import com.yash.shopping.entity.User;
import com.yash.shopping.exception.InvalidCredentialsException;
import com.yash.shopping.repository.UserRepository;
import com.yash.shopping.service.LoginService;

/**
 * @author yash.ghawghawe
 *
 */
@Service
public class LoginServiceImpl implements LoginService {
	
	private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	/**
	 * @throws InvalidCredentialsException 
	 * @see com.yash.shopping.service.LoginService#authenticate(com.yash.shopping.dto.LoginDTO)
	 */
	@Override
	public boolean authenticate(@Valid LoginDTO loginDTO) throws InvalidCredentialsException {
		User user = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
		if (ObjectUtils.isEmpty(user)) {
			logger.info("Wrong Credentials : username/password is wrong");
			throw new InvalidCredentialsException("Wrong Credentials : username/password is wrong");
		}
		return true;
	}

}
