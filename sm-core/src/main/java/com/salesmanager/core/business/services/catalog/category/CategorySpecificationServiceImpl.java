package com.salesmanager.core.business.services.catalog.category;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.category.CategorySpecificationRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.category.CategorySpecification;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("categorySpecificationService")
public class CategorySpecificationServiceImpl extends SalesManagerEntityServiceImpl<Long, CategorySpecification> implements  CategorySpecificationService{

    private CategorySpecificationRepository categorySpecificationRepository;

    @Inject
    public CategorySpecificationServiceImpl(CategorySpecificationRepository categorySpecificationRepository) {
        super(categorySpecificationRepository);
        this.categorySpecificationRepository = categorySpecificationRepository;
    }

    @Override
    public void saveOrUpdate(CategorySpecification specification) throws ServiceException {
        // save or update (persist and attach entities
        if (specification.getId() != null && specification.getId() > 0) {

            super.update(specification);

        } else {

            this.create(specification);

        }
    }

    @Override
    public List<CategorySpecification> specificationsForProduct(Long productId, Integer languageId) {
        return categorySpecificationRepository.specificationsForProduct(productId, languageId);
    }


}
