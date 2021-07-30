package com.salesmanager.shop.model.catalog.brand;

import java.util.List;

public class ReadableBrandFull extends ReadableBrand {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private List<BrandDescription> descriptions;

  public List<BrandDescription> getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(List<BrandDescription> descriptions) {
    this.descriptions = descriptions;
  }

}
