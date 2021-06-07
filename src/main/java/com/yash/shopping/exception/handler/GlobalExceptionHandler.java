package com.yash.shopping.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yash.shopping.dto.MessageResposne;
import com.yash.shopping.exception.InsufficientBalanceException;
import com.yash.shopping.exception.InvalidCredentialsException;
import com.yash.shopping.exception.InvalidUserIdException;
import com.yash.shopping.exception.ProductNotFoundException;
import com.yash.shopping.exception.TransactionFailedException;

/**
 * @author yash.ghawghawe
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * @param InvalidCredentialsException
	 *            e
	 * @return ResponseEntity<MessageResposne>
	 */
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<MessageResposne> handleException(InvalidCredentialsException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new MessageResposne("Wrong Credentials : username/password is wrong"));
	}

	/**
	 * @param ProductNotFoundException
	 *            e
	 * @return ResponseEntity<MessageResposne>
	 */
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<MessageResposne> handleProductNotFoundException(ProductNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResposne("Product Not Found"));
	}

	/**
	 * @param InvalidUserIdException
	 *            e
	 * @return ResponseEntity<MessageResposne>
	 */
	@ExceptionHandler(InvalidUserIdException.class)
	public ResponseEntity<MessageResposne> handleInvalidUserIdException(InvalidUserIdException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResposne("Please Enter Correct UserId"));
	}

	/**
	 * @param TransactionFailedException
	 *            e
	 * @return ResponseEntity<MessageResposne>
	 */
	@ExceptionHandler(TransactionFailedException.class)
	public ResponseEntity<MessageResposne> handleTransactionFailedException(TransactionFailedException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResposne("Transaction Failed"));
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<MessageResposne> handleInsufficientBalanceException(InsufficientBalanceException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResposne("Balance is low for purchase"));
	}

	/**
	 * @param MethodArgumentNotValidException
	 *            ex
	 * @return ResponseEntity
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
