package com.alten.shop.utils;

import java.util.List;

import com.alten.shop.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductWrapper {

	@JsonProperty("data")
	private List<Product> products;
	
	// getters and setters
	
	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
