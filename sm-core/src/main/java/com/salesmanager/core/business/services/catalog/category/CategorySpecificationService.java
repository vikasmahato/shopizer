package com.salesmanager.core.business.services.catalog.category;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategorySpecification;

import java.util.List;

public interface CategorySpecificationService extends SalesManagerEntityService<Long, CategorySpecification> {

    void saveOrUpdate(CategorySpecification specification) throws ServiceException;

    List<CategorySpecification> specificationsForProduct(Long productId, Integer languageId);
}
