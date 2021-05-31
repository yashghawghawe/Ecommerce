package com.yash.shopping.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yash.ghawghawe
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {

	private String accountNumber;
	private String beneficiaryAccountNumber;
	private double amount;

	public TransactionDTO(String accountNumber, String beneficiaryAccountNumber, double amount) {
		super();
		this.accountNumber = accountNumber;
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
		this.amount = amount;
	}

}
