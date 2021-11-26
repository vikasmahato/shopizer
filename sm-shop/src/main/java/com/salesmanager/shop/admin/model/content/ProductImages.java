package com.salesmanager.shop.admin.model.content;

public class ProductImages extends ContentFiles {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7732719188032287938L;
	private long productId;
	private long variantId;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getVariantId() {
		return variantId;
	}

	public void setVariantId(long variantId) {
		this.variantId = variantId;
	}
}
