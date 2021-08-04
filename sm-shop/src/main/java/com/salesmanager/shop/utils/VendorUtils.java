package com.salesmanager.shop.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.salesmanager.core.model.catalog.product.vendor.VendorDescription;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.model.catalog.Vendor;

public class VendorUtils {
  
  
  public static com.salesmanager.shop.admin.model.catalog.Vendor readableVendorConverter(com.salesmanager.core.model.catalog.product.vendor.Vendor vendor, Language language) {
    com.salesmanager.shop.admin.model.catalog.Vendor readableVendor = new com.salesmanager.shop.admin.model.catalog.Vendor();
    readableVendor.setVendor(vendor);

    List<VendorDescription> descriptions = new ArrayList<VendorDescription>(vendor.getDescriptions());

    //descriptions
    //.stream();
    //.filter(desc -> desc.getLanguage().getCode().equals(language.getCode()));


    readableVendor.setDescriptions(descriptions);
    return readableVendor;
  }
  
  public static List<com.salesmanager.shop.admin.model.catalog.Vendor> readableVendorListConverter(List<com.salesmanager.core.model.catalog.product.vendor.Vendor> vendors, Language language) {
    
    List<com.salesmanager.shop.admin.model.catalog.Vendor> readableVendors =
        vendors.stream()
         .map(cat -> readableVendorConverter(cat, language))
         .collect(Collectors.toList());
    
    return readableVendors;
    
  }

}
