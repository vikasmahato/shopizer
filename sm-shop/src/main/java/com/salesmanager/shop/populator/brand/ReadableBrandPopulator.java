package com.salesmanager.shop.populator.brand;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.brand.BrandDescription;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.brand.ReadableBrand;
import com.salesmanager.shop.model.catalog.brand.ReadableBrandFull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReadableBrandPopulator extends
    AbstractDataPopulator<com.salesmanager.core.model.catalog.product.brand.Brand, ReadableBrand> {



  @Override
  public ReadableBrand populate(
      com.salesmanager.core.model.catalog.product.brand.Brand source,
      ReadableBrand target, MerchantStore store, Language language)
      throws ConversionException {


    if (language == null) {
      target = new ReadableBrandFull();
    }
    target.setOrder(source.getOrder());
    target.setId(source.getId());
    target.setCode(source.getCode());
    if (source.getDescriptions() != null && source.getDescriptions().size() > 0) {

      List<com.salesmanager.shop.model.catalog.brand.BrandDescription> fulldescriptions =
          new ArrayList<com.salesmanager.shop.model.catalog.brand.BrandDescription>();

      Set<BrandDescription> descriptions = source.getDescriptions();
      BrandDescription description = null;
      for (BrandDescription desc : descriptions) {
        if (language != null && desc.getLanguage().getCode().equals(language.getCode())) {
          description = desc;
          break;
        } else {
          fulldescriptions.add(populateDescription(desc));
        }
      }



      if (description != null) {
        com.salesmanager.shop.model.catalog.brand.BrandDescription d =
            populateDescription(description);
        target.setDescription(d);
      }

      if (target instanceof ReadableBrandFull) {
        ((ReadableBrandFull) target).setDescriptions(fulldescriptions);
      }

    }



    return target;
  }

  @Override
  protected ReadableBrand createTarget() {
    return null;
  }

  com.salesmanager.shop.model.catalog.brand.BrandDescription populateDescription(
      BrandDescription description) {
    if (description == null) {
      return null;
    }
    com.salesmanager.shop.model.catalog.brand.BrandDescription d =
        new com.salesmanager.shop.model.catalog.brand.BrandDescription();
    d.setName(description.getName());
    d.setDescription(description.getDescription());
    d.setId(description.getId());
    d.setTitle(description.getTitle());
    if (description.getLanguage() != null) {
      d.setLanguage(description.getLanguage().getCode());
    }
    return d;
  }

}
