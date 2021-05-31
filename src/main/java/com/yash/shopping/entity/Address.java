package com.yash.shopping.entity;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yash.ghawghawe
 *
 */
@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {

	private String city;
	private String state;
	private String town;
	private String pincode;

	
}
