package com.yash.shopping.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yash.shopping.dto.TransactionDTO;
import com.yash.shopping.exception.TransactionFailedException;

/**
 * @author yash.ghawghawe
 *
 */
@FeignClient(name="http://BANK-SERVICE/bank/transactions")
public interface BankClient {

	/**
	 * @param transactionDTO
	 * @return String
	 * @throws TransactionFailedException
	 */
	@PostMapping
	public String transferFund(@RequestBody TransactionDTO transactionDTO) throws TransactionFailedException;
}
