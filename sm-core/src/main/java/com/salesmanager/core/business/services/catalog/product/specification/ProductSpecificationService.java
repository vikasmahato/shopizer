package com.salesmanager.core.business.services.catalog.product.specification;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;

import java.util.List;

public interface ProductSpecificationService extends SalesManagerEntityService<Long, ProductSpecificationVariant> {

    void saveOrUpdate(ProductSpecificationVariant specificationVariant) throws ServiceException;

    List<ProductSpecificationVariant> getBySpecificationId(Long productId, Long specificationId);

    ProductSpecificationVariant getBySpecificationIdAndValue(Long productId, Long specificationId, String value);
}
