package com.salesmanager.shop.store.controller.brand.facade;

import java.util.List;

import com.salesmanager.core.model.catalog.product.brand.Brand;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.brand.PersistableBrand;
import com.salesmanager.shop.model.catalog.brand.ReadableBrand;
import com.salesmanager.shop.model.catalog.brand.ReadableBrandList;
import com.salesmanager.shop.model.entity.ListCriteria;

/**
 * brand / brand / collection product grouping
 * @author carlsamson
 *
 */
public interface BrandFacade {
  
  List<ReadableBrand> getByProductInCategory(MerchantStore store, Language language, Long categoryId);
  
  /**
   * Creates or saves a brand
   * 
   * @param brand
   * @param store
   * @param language
   * @throws Exception
   */
  void saveOrUpdatebrand(PersistableBrand brand, MerchantStore store,
                         Language language) throws Exception;

  /**
   * Deletes a brand
   * 
   * @param brand
   * @param store
   * @param language
   * @throws Exception
   */
  void deletebrand(Brand brand, MerchantStore store, Language language)
      throws Exception;

  /**
   * Get a brand by id
   * 
   * @param id
   * @param store
   * @param language
   * @return
   * @throws Exception
   */
  ReadableBrand getbrand(Long id, MerchantStore store, Language language)
      throws Exception;

  /**
   * Get all brand
   * 
   * @param store
   * @param language
   * @return
   * @throws Exception
   */
  ReadableBrandList getAllbrands(MerchantStore store, Language language, ListCriteria criteria, int page, int count) ;
  
  /**
   * List brands by a specific store
   * @param store
   * @param language
   * @param criteria
   * @param page
   * @param count
   * @return
   */
  ReadableBrandList listByStore(MerchantStore store, Language language, ListCriteria criteria, int page, int count) ;
  
  /**
   * Determines if brand code already exists
   * @param store
   * @param brandCode
   * @return
   */
  boolean brandExist(MerchantStore store, String brandCode);

}
