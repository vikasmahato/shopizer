package com.salesmanager.shop.store.controller.items;

import com.salesmanager.core.business.services.catalog.product.brand.BrandService;
import com.salesmanager.core.model.catalog.product.brand.Brand;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.catalog.brand.ReadableBrand;
import com.salesmanager.shop.model.shop.PageInformation;
import com.salesmanager.shop.populator.brand.ReadableBrandPopulator;
import com.salesmanager.shop.store.controller.ControllerConstants;
import com.salesmanager.shop.utils.PageBuilderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Drives various product listings
 * @author carlsamson
 *
 */
@Controller
public class ListItemsController {
	
	@Inject
	BrandService brandService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ListItemsController.class);
	
	@RequestMapping("/shop/listing/{url}.html")
	public String displayListingPage(@PathVariable String url, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		
		Language language = (Language)request.getAttribute("LANGUAGE");
		
		//brand brand = brandService.getByUrl(store, language, url); // this needs to be checked

		Brand brand =null;
		
		if(brand==null) {
			LOGGER.error("No brand found for url " + url);
			//redirect on page not found
			return PageBuilderUtils.build404(store);
			
		}
		
		ReadableBrand readablebrand = new ReadableBrand();
		
		ReadableBrandPopulator populator = new ReadableBrandPopulator();
		readablebrand = populator.populate(brand, readablebrand, store, language);
		
		//meta information
		PageInformation pageInformation = new PageInformation();
		pageInformation.setPageDescription(readablebrand.getDescription().getMetaDescription());
		pageInformation.setPageKeywords(readablebrand.getDescription().getKeyWords());
		pageInformation.setPageTitle(readablebrand.getDescription().getTitle());
		pageInformation.setPageUrl(readablebrand.getDescription().getFriendlyUrl());
		
		model.addAttribute("brand", readablebrand);
		
		/** template **/
		StringBuilder template = new StringBuilder().append(ControllerConstants.Tiles.Items.items_brand).append(".").append(store.getStoreTemplate());

		return template.toString();
	}
	

}
