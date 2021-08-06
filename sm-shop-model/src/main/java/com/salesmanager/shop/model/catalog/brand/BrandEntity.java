package com.salesmanager.shop.model.catalog.brand;

import java.io.Serializable;



public class BrandEntity extends Brand implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int order;

	public void setOrder(int order) {
		this.order = order;
	}
	public int getOrder() {
		return order;
	}


}
