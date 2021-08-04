package com.salesmanager.shop.mapper.catalog;

import java.util.Optional;
import java.util.Set;

import com.salesmanager.core.model.catalog.product.brand.Brand;
import com.salesmanager.core.model.catalog.product.brand.BrandDescription;
import com.salesmanager.shop.model.catalog.brand.ReadableBrand;
import org.springframework.stereotype.Component;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;

@Component
public class ReadableBrandMapper implements Mapper<Brand, ReadableBrand> {

  @Override
  public ReadableBrand convert(Brand source, MerchantStore store, Language language) {

	if(language == null) {
		language = store.getDefaultLanguage();
	}
    ReadableBrand target = new ReadableBrand();

    Optional<com.salesmanager.shop.model.catalog.brand.BrandDescription> description =
        getDescription(source, language, target);
    description.ifPresent(target::setDescription);

    target.setCode(source.getCode());
    target.setId(source.getId());
    target.setOrder(source.getOrder());
    Optional<com.salesmanager.shop.model.catalog.brand.BrandDescription> desc = this.getDescription(source, language, target);
    if(description.isPresent()) {
    	target.setDescription(desc.get());
    }
    

    return target;
  }

  private Optional<com.salesmanager.shop.model.catalog.brand.BrandDescription> getDescription(
      Brand source, Language language, ReadableBrand target) {

    Optional<BrandDescription> description =
        getDescription(source.getDescriptions(), language);
    if (source.getDescriptions() != null && !source.getDescriptions().isEmpty()
        && description.isPresent()) {
      return Optional.of(convertDescription(description.get(), source));
    } else {
      return Optional.empty();
    }
  }

  private Optional<BrandDescription> getDescription(
      Set<BrandDescription> descriptionsLang, Language language) {
    Optional<BrandDescription> descriptionByLang = descriptionsLang.stream()
        .filter(desc -> desc.getLanguage().getCode().equals(language.getCode())).findAny();
    if (descriptionByLang.isPresent()) {
      return descriptionByLang;
    } else {
      return Optional.empty();
    }
  }

  private com.salesmanager.shop.model.catalog.brand.BrandDescription convertDescription(
      BrandDescription description, Brand source) {
    final com.salesmanager.shop.model.catalog.brand.BrandDescription desc =
        new com.salesmanager.shop.model.catalog.brand.BrandDescription();

    desc.setFriendlyUrl(description.getUrl());
    desc.setId(description.getId());
    desc.setLanguage(description.getLanguage().getCode());
    desc.setName(description.getName());
    desc.setDescription(description.getDescription());
    return desc;
  }

  @Override
  public ReadableBrand merge(Brand source, ReadableBrand destination,
                                    MerchantStore store, Language language) {
    return destination;
  }

}
