package com.salesmanager.shop.admin.model.catalog;

import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.availability.ProductsAvailable;
import com.salesmanager.core.model.catalog.product.price.ProductPriceDescription;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductPrice {
	
	@Valid
	private com.salesmanager.core.model.catalog.product.price.ProductPrice price = null;
	@Valid
	private List <ProductPriceDescription> descriptions = new ArrayList<ProductPriceDescription>();
	private String priceText;
	private String dealerPrice;
	private String listPrice;
	private String specialPriceText;
	private ProductAvailability productAvailability;
	private Set<ProductsAvailable> productsAvailable;
	
	
	//cannot convert in this object to date ??? needs to use a string, parse, bla bla
	private String productPriceSpecialStartDate;
	private String productPriceSpecialEndDate;
	
	private com.salesmanager.core.model.catalog.product.Product product;
	
	
	
	
	
	public List <ProductPriceDescription> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List <ProductPriceDescription> descriptions) {
		this.descriptions = descriptions;
	}
	public ProductAvailability getProductAvailability() {
		return productAvailability;
	}
	public void setProductAvailability(ProductAvailability productAvailability) {
		this.productAvailability = productAvailability;
	}
	public String getPriceText() {
		return priceText;
	}
	public void setPriceText(String priceText) {
		this.priceText = priceText;
	}
	public com.salesmanager.core.model.catalog.product.price.ProductPrice getPrice() {
		return price;
	}
	public void setPrice(com.salesmanager.core.model.catalog.product.price.ProductPrice price) {
		this.price = price;
	}
	public String getSpecialPriceText() {
		return specialPriceText;
	}
	public void setSpecialPriceText(String specialPriceText) {
		this.specialPriceText = specialPriceText;
	}

	public com.salesmanager.core.model.catalog.product.Product getProduct() {
		return product;
	}
	public void setProduct(com.salesmanager.core.model.catalog.product.Product product) {
		this.product = product;
	}
	public String getProductPriceSpecialStartDate() {
		return productPriceSpecialStartDate;
	}
	public void setProductPriceSpecialStartDate(
			String productPriceSpecialStartDate) {
		this.productPriceSpecialStartDate = productPriceSpecialStartDate;
	}
	public String getProductPriceSpecialEndDate() {
		return productPriceSpecialEndDate;
	}
	public void setProductPriceSpecialEndDate(String productPriceSpecialEndDate) {
		this.productPriceSpecialEndDate = productPriceSpecialEndDate;
	}

	public Set<ProductsAvailable> getProductsAvailable() {
		return productsAvailable;
	}

	public void setProductsAvailable(Set<ProductsAvailable> productsAvailable) {
		this.productsAvailable = productsAvailable;
	}

	public String getDealerPrice() {
		return dealerPrice;
	}

	public void setDealerPrice(String dealerPrice) {
		this.dealerPrice = dealerPrice;
	}

	public String getListPrice() {
		return listPrice;
	}

	public void setListPrice(String listPrice) {
		this.listPrice = listPrice;
	}
}
