package com.yash.shopping.service;

import javax.validation.Valid;


import com.yash.shopping.dto.LoginDTO;
import com.yash.shopping.exception.InvalidCredentialsException;

/**
 * @author yash.ghawghawe
 *
 */
public interface LoginService {

	/**
	 * @param loginDTO
	 * @return boolean
	 * @throws InvalidCredentialsException 
	 */
	boolean authenticate(@Valid LoginDTO loginDTO) throws InvalidCredentialsException ;

}
