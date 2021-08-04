package com.salesmanager.shop.model.catalog.brand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersistableBrand extends BrandEntity implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<BrandDescription> descriptions = new ArrayList<BrandDescription>();
	public void setDescriptions(List<BrandDescription> descriptions) {
		this.descriptions = descriptions;
	}
	public List<BrandDescription> getDescriptions() {
		return descriptions;
	}

}
