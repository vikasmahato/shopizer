package com.salesmanager.core.business.services.catalog.product.brand;


import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.brand.BrandRepository;
import com.salesmanager.core.business.repositories.catalog.product.brand.PageableBrandRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.brand.Brand;
import com.salesmanager.core.model.catalog.product.brand.BrandDescription;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import org.jsoup.helper.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;



@Service("brandService")
public class BrandServiceImpl extends SalesManagerEntityServiceImpl<Long, Brand>
    implements BrandService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BrandServiceImpl.class);

  @Inject
  private PageableBrandRepository pageablebrandRepository;
  
  private BrandRepository brandRepository;

  @Inject
  public BrandServiceImpl(BrandRepository brandRepository) {
    super(brandRepository);
    this.brandRepository = brandRepository;
  }

  @Override
  public void delete(Brand brand) throws ServiceException {
    brand = this.getById(brand.getId());
    super.delete(brand);
  }

  @Override
  public Long getCountManufAttachedProducts(Brand brand) throws ServiceException {
    return brandRepository.countByProduct(brand.getId());
    // .getCountManufAttachedProducts( brand );
  }


  @Override
  public List<Brand> listByStore(MerchantStore store, Language language)
      throws ServiceException {
    return brandRepository.findByStoreAndLanguage(store.getId(), language.getId());
  }

  @Override
  public List<Brand> listByStore(MerchantStore store) throws ServiceException {
    return brandRepository.findByStore(store.getId());
  }

  @Override
  public List<Brand> listByProductsByCategoriesId(MerchantStore store, List<Long> ids,
      Language language) throws ServiceException {
    return brandRepository.findByCategoriesAndLanguage(ids, language.getId());
  }

  @Override
  public void addbrandDescription(Brand brand,
      BrandDescription description) throws ServiceException {


    if (brand.getDescriptions() == null) {
      brand.setDescriptions(new HashSet<BrandDescription>());
    }

    brand.getDescriptions().add(description);
    description.setbrand(brand);
    update(brand);
  }

  @Override
  public void saveOrUpdate(Brand brand) throws ServiceException {

    LOGGER.debug("Creating brand");

    if (brand.getId() != null && brand.getId() > 0) {
      super.update(brand);

    } else {
      super.create(brand);

    }
  }

  @Override
  public Brand getByCode(com.salesmanager.core.model.merchant.MerchantStore store,
      String code) {
    return brandRepository.findByCodeAndMerchandStore(code, store.getId());
  }
  
  @Override
  public Brand getById(Long id) {
    return brandRepository.findOne(id);
  }

  @Override
  public List<Brand> listByProductsInCategory(MerchantStore store, Category category,
      Language language) throws ServiceException {
    Validate.notNull(store, "Store cannot be null");
    Validate.notNull(category,"Category cannot be null");
    Validate.notNull(language, "Language cannot be null");
    return brandRepository.findByProductInCategoryId(store.getId(), category.getLineage(), language.getId());
  }

  @Override
  public Page<Brand> listByStore(MerchantStore store, Language language, int page, int count)
      throws ServiceException {

    Pageable pageRequest = PageRequest.of(page, count);
    return pageablebrandRepository.findByStore(store.getId(), language.getId(), null, pageRequest);
  }

  @Override
  public int count(MerchantStore store) {
    Validate.notNull(store, "Merchant must not be null");
    return brandRepository.count(store.getId());
  }

  @Override
  public Page<Brand> listByStore(MerchantStore store, Language language, String name,
      int page, int count) throws ServiceException {

    Pageable pageRequest = PageRequest.of(page, count);
    return pageablebrandRepository.findByStore(store.getId(), language.getId(), name, pageRequest);
  }

  @Override
  public Page<Brand> listByStore(MerchantStore store, String name, int page, int count)
      throws ServiceException {

    Pageable pageRequest = PageRequest.of(page, count);
    return pageablebrandRepository.findByStore(store.getId(), name, pageRequest);
  }
}
