package com.salesmanager.shop.model.catalog.brand;

import java.io.Serializable;

public class ReadableBrand extends BrandEntity implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BrandDescription description;
	public void setDescription(BrandDescription description) {
		this.description = description;
	}
	public BrandDescription getDescription() {
		return description;
	}

}
