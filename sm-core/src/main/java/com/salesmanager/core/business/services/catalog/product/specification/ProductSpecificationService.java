package com.salesmanager.core.business.services.catalog.product.specification;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;

public interface ProductSpecificationService extends SalesManagerEntityService<Long, ProductSpecificationVariant> {

    void saveOrUpdate(ProductSpecificationVariant specificationVariant);
}
