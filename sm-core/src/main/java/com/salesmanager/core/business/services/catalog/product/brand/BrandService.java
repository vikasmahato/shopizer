package com.salesmanager.core.business.services.catalog.product.brand;

import java.util.List;

import com.salesmanager.core.model.catalog.product.brand.Brand;
import com.salesmanager.core.model.catalog.product.brand.BrandDescription;
import org.springframework.data.domain.Page;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;

public interface BrandService extends SalesManagerEntityService<Long, Brand> {

	List<Brand> listByStore(MerchantStore store, Language language)
			throws ServiceException;

	List<Brand> listByStore(MerchantStore store) throws ServiceException;
	
	Page<Brand> listByStore(MerchantStore store, Language language, int page, int count) throws ServiceException;

	Page<Brand> listByStore(MerchantStore store, Language language, String name, int page, int count) throws ServiceException;
	
	void saveOrUpdate(Brand brand) throws ServiceException;
	
	void addbrandDescription(Brand brand, BrandDescription description) throws ServiceException;
	
	Long getCountManufAttachedProducts( Brand brand )  throws ServiceException;
	
	void delete(Brand brand) throws ServiceException;
	
	Brand getByCode(MerchantStore store, String code);

	/**
	 * List brands by products from a given list of categories
	 * @param store
	 * @param ids
	 * @param language
	 * @return
	 * @throws ServiceException
	 */
	List<Brand> listByProductsByCategoriesId(MerchantStore store,
			List<Long> ids, Language language) throws ServiceException;
	
	/**
	 * List by product in category lineage
	 * @param store
	 * @param category
	 * @param language
	 * @return
	 * @throws ServiceException
	 */
	List<Brand> listByProductsInCategory(MerchantStore store,
        Category category, Language language) throws ServiceException;
	
	Page<Brand> listByStore(MerchantStore store, String name,
	      int page, int count) throws ServiceException;
	
	int count(MerchantStore store);

	
}
