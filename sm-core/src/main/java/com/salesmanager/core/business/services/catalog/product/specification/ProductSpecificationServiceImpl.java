package com.salesmanager.core.business.services.catalog.product.specification;

import com.salesmanager.core.business.repositories.catalog.category.CategorySpecificationRepository;
import com.salesmanager.core.business.repositories.catalog.product.specification.ProductSpecificationRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("productSpecificationService")
public class ProductSpecificationServiceImpl  extends SalesManagerEntityServiceImpl<Long, ProductSpecificationVariant> implements ProductSpecificationService {

    private ProductSpecificationRepository specificationRepository;

    @Inject
    public ProductSpecificationServiceImpl(ProductSpecificationRepository specificationRepository) {
        super(specificationRepository);
        this.specificationRepository = specificationRepository;
    }

    @Override
    public void saveOrUpdate(ProductSpecificationVariant specificationVariant) {

    }
}
