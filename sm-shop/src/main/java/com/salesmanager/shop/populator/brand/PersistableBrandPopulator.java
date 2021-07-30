
package com.salesmanager.shop.populator.brand;

import java.util.HashSet;
import java.util.Set;

import com.salesmanager.core.model.catalog.product.brand.Brand;
import com.salesmanager.shop.model.catalog.brand.BrandDescription;
import com.salesmanager.shop.model.catalog.brand.PersistableBrand;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;


/**
 * @author Carl Samson
 *
 */


public class PersistableBrandPopulator extends AbstractDataPopulator<PersistableBrand, Brand>
{
	
	
	private LanguageService languageService;

	@Override
	public Brand populate(PersistableBrand source,
			Brand target, MerchantStore store, Language language)
			throws ConversionException {
		
		Validate.notNull(languageService, "Requires to set LanguageService");
		
		try {
			
			target.setMerchantStore(store);
			target.setCode(source.getCode());
			

			if(!CollectionUtils.isEmpty(source.getDescriptions())) {
				Set<com.salesmanager.core.model.catalog.product.brand.BrandDescription> descriptions = new HashSet<com.salesmanager.core.model.catalog.product.brand.BrandDescription>();
				for(BrandDescription description : source.getDescriptions()) {
					com.salesmanager.core.model.catalog.product.brand.BrandDescription desc = new com.salesmanager.core.model.catalog.product.brand.BrandDescription();
					if(desc.getId() != null && desc.getId().longValue()>0) {
						desc.setId(description.getId());
					}
					if(target.getDescriptions() != null) {
						for(com.salesmanager.core.model.catalog.product.brand.BrandDescription d : target.getDescriptions()) {
							if(d.getLanguage().getCode().equals(description.getLanguage()) || desc.getId() != null && d.getId().longValue() == desc.getId().longValue()) {
								desc = d;
							}
						}
					}
					
					desc.setbrand(target);
					desc.setDescription(description.getDescription());
					desc.setName(description.getName());
					Language lang = languageService.getByCode(description.getLanguage());
					if(lang==null) {
						throw new ConversionException("Language is null for code " + description.getLanguage() + " use language ISO code [en, fr ...]");
					}
					desc.setLanguage(lang);
					descriptions.add(desc);
				}
				target.setDescriptions(descriptions);
			}
		
		} catch (Exception e) {
			throw new ConversionException(e);
		}
	
		
		return target;
	}

	@Override
	protected Brand createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	public LanguageService getLanguageService() {
		return languageService;
	}


}
