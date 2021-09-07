package com.salesmanager.core.business.services.catalog.product.availability;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.availability.ProductsAvailable;

import java.util.List;
import java.util.Set;

public interface ProductsAvailableService extends
        SalesManagerEntityService<Long, ProductsAvailable> {

    Long getLastAvailId();

    ProductsAvailable getByProductVariant(Long productId, Long variantId);

    ProductsAvailable getByProduct(Long productId);

    List<ProductsAvailable> getByVariants(List<Long> variants);

    Set<ProductsAvailable> getByAvailId(Long avail_id);

    ProductsAvailable getByVariant(List<Long> variants);
}
