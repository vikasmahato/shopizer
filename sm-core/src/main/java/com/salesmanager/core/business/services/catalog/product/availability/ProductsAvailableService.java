package com.salesmanager.core.business.services.catalog.product.availability;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.availability.ProductsAvailable;

public interface ProductsAvailableService extends
        SalesManagerEntityService<Long, ProductsAvailable> {

    Long getLastAvailId();

    ProductsAvailable getByProductVariant(Long productId, Long variantId);

    ProductsAvailable getByProduct(Long productId);

}
