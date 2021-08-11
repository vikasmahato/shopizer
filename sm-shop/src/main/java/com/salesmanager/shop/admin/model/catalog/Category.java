package com.salesmanager.shop.admin.model.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import com.salesmanager.core.model.catalog.category.CategoryDescription;
import com.salesmanager.core.model.catalog.category.CategorySpecification;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;

/**
 * Wrapper to ease admin jstl
 * @author carlsamson
 *
 */
public class Category implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private com.salesmanager.core.model.catalog.category.Category category;
  
  @Valid
  private List<CategoryDescription> descriptions = new ArrayList<CategoryDescription>();

  @Valid
  private List<CategorySpecification> specifications = new ArrayList<CategorySpecification>();

  @Valid
  private List<ProductSpecificationVariant> productSpecifications = new ArrayList<ProductSpecificationVariant>();

  public List<ProductSpecificationVariant> getProductSpecifications() {
    return productSpecifications;
  }

  public void setProductSpecifications(List<ProductSpecificationVariant> productSpecifications) {
    this.productSpecifications = productSpecifications;
  }

  public com.salesmanager.core.model.catalog.category.Category getCategory() {
    return category;
  }

  public void setCategory(com.salesmanager.core.model.catalog.category.Category category) {
    this.category = category;
  }

  public List<CategoryDescription> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(List<CategoryDescription> descriptions) {
    this.descriptions = descriptions;
  }

  public List<CategorySpecification> getSpecifications() {
    return specifications;
  }

  public void setSpecifications(List<CategorySpecification> specifications) {
    this.specifications = specifications;
  }
}
