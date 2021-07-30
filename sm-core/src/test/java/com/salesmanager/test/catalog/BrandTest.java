package com.salesmanager.test.catalog;

import com.salesmanager.core.model.catalog.product.brand.Brand;
import com.salesmanager.core.model.catalog.product.brand.BrandDescription;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;


public class BrandTest extends com.salesmanager.test.common.AbstractSalesManagerCoreTestCase {

	/**
	 * This method creates multiple products using multiple catalog APIs
	 * @throws ServiceException
	 */
	@Test
	public void testbrand() throws Exception {

	    Language en = languageService.getByCode("en");

	    MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);

	    Brand oreilley = new Brand();
	    oreilley.setMerchantStore(store);
	    oreilley.setCode("oreilley2");

	    BrandDescription oreilleyd = new BrandDescription();
	    oreilleyd.setLanguage(en);
	    oreilleyd.setName("O\'reilley");
	    oreilleyd.setbrand(oreilley);
	    oreilley.getDescriptions().add(oreilleyd);

	    brandService.create(oreilley);

	    Brand packed = new Brand();
	    packed.setMerchantStore(store);
	    packed.setCode("packed2");

	    BrandDescription packedd = new BrandDescription();
	    packedd.setLanguage(en);
	    packedd.setbrand(packed);
	    packedd.setName("Packed publishing");
	    packed.getDescriptions().add(packedd);

	    brandService.create(packed);

	    Brand novells = new Brand();
	    novells.setMerchantStore(store);
	    novells.setCode("novells2");

	    BrandDescription novellsd = new BrandDescription();
	    novellsd.setLanguage(en);
	    novellsd.setbrand(novells);
	    novellsd.setName("Novells publishing");
	    novells.getDescriptions().add(novellsd);

	    brandService.create(novells);


		//query pageable category
	    Page<Brand> pageable = brandService.listByStore(store, en, 0, 5);
	    Assert.isTrue(pageable.getSize()>0, "4 brands");
	    
	}


	




}