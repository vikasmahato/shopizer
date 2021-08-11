package com.salesmanager.core.business.services.catalog.product.specification;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.category.CategorySpecificationRepository;
import com.salesmanager.core.business.repositories.catalog.product.specification.ProductSpecificationRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("productSpecificationService")
public class ProductSpecificationServiceImpl  extends SalesManagerEntityServiceImpl<Long, ProductSpecificationVariant> implements ProductSpecificationService {

    private ProductSpecificationRepository specificationRepository;

    @Inject
    public ProductSpecificationServiceImpl(ProductSpecificationRepository specificationRepository) {
        super(specificationRepository);
        this.specificationRepository = specificationRepository;
    }

    @Override
    public void saveOrUpdate(ProductSpecificationVariant specification) throws ServiceException {
        if (specification.getId() != null && specification.getId() > 0) {

            super.update(specification);

        } else {

            this.create(specification);

        }
    }

    @Override
    public List<ProductSpecificationVariant> getBySpecificationId(Long id){
        return specificationRepository.getBySpecificationId(id);
    }

    @Override
    public ProductSpecificationVariant getBySpecificationIdAndValue(Long specificationId, String value){
        return specificationRepository.getBySpecificationIdAndValue(specificationId, value);
    }
}