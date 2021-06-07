package com.yash.shopping.entity;

import java.util.function.Consumer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yash.ghawghawe
 *
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderDetailsId;
	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private long userId;
	private double price;
	private String productname;
	private int quantity;

	public OrderDetails(Consumer<OrderDetails> builder) {
		builder.accept(this);
	}

}
