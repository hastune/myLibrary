package com.sm.response;

import com.sm.beans.product;
import com.sm.beans.sold;

public class soldResp {
	
	private sold sold;
	private product product;
	
	public sold getSold() {
		return sold;
	}
	public void setSold(sold sold) {
		this.sold = sold;
	}
	public product getProduct() {
		return product;
	}
	public void setProduct(product product) {
		this.product = product;
	}
	
	
}
