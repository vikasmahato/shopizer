package com.salesmanager.shop.store.facade.brand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

import com.salesmanager.core.business.services.catalog.product.brand.BrandService;
import com.salesmanager.core.model.catalog.product.brand.Brand;
import com.salesmanager.shop.model.catalog.brand.PersistableBrand;
import com.salesmanager.shop.model.catalog.brand.ReadableBrand;
import com.salesmanager.shop.model.catalog.brand.ReadableBrandList;
import com.salesmanager.shop.populator.brand.PersistableBrandPopulator;
import com.salesmanager.shop.populator.brand.ReadableBrandPopulator;
import com.salesmanager.shop.store.controller.brand.facade.BrandFacade;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.entity.ListCriteria;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;

@Service("brandFacade")
public class BrandFacadeImpl implements BrandFacade {

  @Inject
  private Mapper<Brand, ReadableBrand> readablebrandConverter;


  @Autowired
  private BrandService brandService;
  
  @Autowired
  private CategoryService categoryService;
  
  @Inject
  private LanguageService languageService;

  @Override
  public List<ReadableBrand> getByProductInCategory(MerchantStore store, Language language,
      Long categoryId) {
    Validate.notNull(store,"MerchantStore cannot be null");
    Validate.notNull(language, "Language cannot be null");
    Validate.notNull(categoryId,"Category id cannot be null");
    
    Category category = categoryService.getById(categoryId, store.getId());
    
    if(category == null) {
      throw new ResourceNotFoundException("Category with id [" + categoryId + "] not found");
    }
    
    if(category.getMerchantStore().getId().longValue() != store.getId().longValue()) {
      throw new UnauthorizedException("Merchant [" + store.getCode() + "] not authorized");
    }
    
    try {
      List<Brand> brands = brandService.listByProductsInCategory(store, category, language);
      
      List<ReadableBrand> brandsList = brands.stream()
    	.sorted(new Comparator<Brand>() {
    	            @Override
    	            public int compare(final Brand object1, final Brand object2) {
    	                return object1.getCode().compareTo(object2.getCode());
    	            }
    	 })
        .map(manuf -> readablebrandConverter.convert(manuf, store, language))
        .collect(Collectors.toList());
      
      return brandsList;
    } catch (ServiceException e) {
      throw new ServiceRuntimeException(e);
    }

  }

  @Override
  public void saveOrUpdatebrand(PersistableBrand brand, MerchantStore store,
                                Language language) throws Exception {

    PersistableBrandPopulator populator = new PersistableBrandPopulator();
    populator.setLanguageService(languageService);


    Brand manuf = new Brand();
  
    if(brand.getId() != null && brand.getId().longValue() > 0) {
    	manuf = brandService.getById(brand.getId());
    	if(manuf == null) {
    		throw new ResourceNotFoundException("brand with id [" + brand.getId() + "] not found");
    	}
    	
    	if(manuf.getMerchantStore().getId().intValue() != store.getId().intValue()) {
    		throw new ResourceNotFoundException("brand with id [" + brand.getId() + "] not found for store [" + store.getId() + "]");
    	}
    }

    populator.populate(brand, manuf, store, language);

    brandService.saveOrUpdate(manuf);

    brand.setId(manuf.getId());

  }

  @Override
  public void deletebrand(Brand brand, MerchantStore store, Language language)
      throws Exception {
    brandService.delete(brand);

  }

  @Override
  public ReadableBrand getbrand(Long id, MerchantStore store, Language language)
      throws Exception {
    Brand brand = brandService.getById(id);
    
    

    if (brand == null) {
      throw new ResourceNotFoundException("brand [" + id + "] not found");
    }
    
    if(brand.getMerchantStore().getId() != store.getId()) {
      throw new ResourceNotFoundException("brand [" + id + "] not found for store [" + store.getId() + "]");
    }

    ReadableBrand readablebrand = new ReadableBrand();

    ReadableBrandPopulator populator = new ReadableBrandPopulator();
    readablebrand = populator.populate(brand, readablebrand, store, language);


    return readablebrand;
  }

  @Override
  public ReadableBrandList getAllbrands(MerchantStore store, Language language, ListCriteria criteria, int page, int count) {

    ReadableBrandList readableList = new ReadableBrandList();
    try {
      /**
       * Is this a pageable request
       */

      List<Brand> brands = null;
      if(page == 0 && count == 0) {
    	//need total count
        int total = brandService.count(store);

        if(language != null) {
          brands = brandService.listByStore(store, language);
        } else {
          brands = brandService.listByStore(store);
        }
        readableList.setRecordsTotal(total);
        readableList.setNumber(brands.size());
      } else {

        Page<Brand> m = null;
        if(language != null) {
          m = brandService.listByStore(store, language, criteria.getName(), page, count);
        } else {
          m = brandService.listByStore(store, criteria.getName(), page, count);
        }
        brands = m.getContent();
        readableList.setTotalPages(m.getTotalPages());
        readableList.setRecordsTotal(m.getTotalElements());
        readableList.setNumber(m.getNumber());
      }

      
      ReadableBrandPopulator populator = new ReadableBrandPopulator();
      List<ReadableBrand> returnList = new ArrayList<ReadableBrand>();
  
      for (Brand m : brands) {
        ReadableBrand readablebrand = new ReadableBrand();
        populator.populate(m, readablebrand, store, language);
        returnList.add(readablebrand);
      }

      readableList.setbrands(returnList);
      return readableList;
      
    } catch (Exception e) {
      throw new ServiceRuntimeException("Error while get brands",e);
    }
  }


  @Override
  public boolean brandExist(MerchantStore store, String brandCode) {
    Validate.notNull(store,"Store must not be null");
    Validate.notNull(brandCode,"brand code must not be null");
    boolean exists = false;
    Brand brand = brandService.getByCode(store, brandCode);
    if(brand!=null) {
      exists = true;
    }
    return exists;
  }

@Override
public ReadableBrandList listByStore(MerchantStore store, Language language, ListCriteria criteria, int page,
		int count) {
	
	ReadableBrandList readableList = new ReadableBrandList();

    try {
        /**
         * Is this a pageable request
         */

        List<Brand> brands = null;

        Page<Brand> m = null;
        if(language != null) {
            m = brandService.listByStore(store, language, criteria.getName(), page, count);
        } else {
            m = brandService.listByStore(store, criteria.getName(), page, count);
        }
        brands = m.getContent();
        readableList.setTotalPages(m.getTotalPages());
        readableList.setRecordsTotal(m.getTotalElements());
        readableList.setNumber(m.getNumber());


        
        ReadableBrandPopulator populator = new ReadableBrandPopulator();
        List<ReadableBrand> returnList = new ArrayList<ReadableBrand>();
    
        for (Brand mf : brands) {
          ReadableBrand readablebrand = new ReadableBrand();
          populator.populate(mf, readablebrand, store, language);
          returnList.add(readablebrand);
        }

        readableList.setbrands(returnList);
        return readableList;
        
      } catch (Exception e) {
        throw new ServiceRuntimeException("Error while get brands",e);
      }
	
}


}
