/*
 *
 *  * Copyright 2003-2015 Monitise Group Limited. All Rights Reserved.
 *  *
 *  * Save to the extent permitted by law, you may not use, copy, modify,
 *  * distribute or create derivative works of this material or any part
 *  * of it without the prior written consent of Monitise Group Limited.
 *  * Any reproduction of this material must contain this notice.
 *
 */

package com.ash.automation.amazon.products.crud;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by az on 27/05/2016.
 */
@Entity
public class Product {
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private String url;

	@Column(name = "expected_Price")
	private String expectedPrice;

	private String currentPrice = "";

	public Product(){

	}

	@Override
	public String toString() {
		return String.format("Customer[id=%d, name='%s', url='%s', expectedPrice='%s']", id, name, url, expectedPrice);
	}

	public Product(String name, String url, String expectedPrice) {
		this.name = name;
		this.url = url;
		this.expectedPrice = expectedPrice;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExpectedPrice() {
		return expectedPrice;
	}

	public void setExpectedPrice(String expectedPrice) {
		this.expectedPrice = expectedPrice;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
}
