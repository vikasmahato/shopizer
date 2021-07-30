package com.salesmanager.shop.model.catalog.brand;

import java.util.ArrayList;
import java.util.List;
import com.salesmanager.shop.model.entity.ReadableList;

public class ReadableBrandList extends ReadableList {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private List<ReadableBrand> brands = new ArrayList<ReadableBrand>();

  public List<ReadableBrand> getbrands() {
    return brands;
  }

  public void setbrands(List<ReadableBrand> brands) {
    this.brands = brands;
  }

}
