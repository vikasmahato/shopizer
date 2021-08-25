package com.salesmanager.core.business.services.catalog.product.specification;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.category.CategorySpecificationRepository;
import com.salesmanager.core.business.repositories.catalog.product.specification.ProductSpecificationRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productSpecificationService")
public class ProductSpecificationServiceImpl  extends SalesManagerEntityServiceImpl<Long, ProductSpecificationVariant> implements ProductSpecificationService {

    @PersistenceContext
    private EntityManager em;

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
    public List<ProductSpecificationVariant> getBySpecificationId(Long productId, Long specificationId){
        return specificationRepository.getBySpecificationId(productId, specificationId);
    }

    @Override
    public ProductSpecificationVariant getBySpecificationIdAndValue(Long productId, Long specificationId, String value){
        return specificationRepository.getBySpecificationIdAndValue(productId, specificationId, value);
    }

    @Override
    public Map<String, String> getSpecificationNameValue(Long productId) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select cs.specification, ps.value from ProductSpecificationVariant ps, CategorySpecification cs ");
        queryBuilder.append("where ps.specification.id = cs.id and ps.product.id = :productId and cs.variant = false");


        String hql = queryBuilder.toString();
        Query q = this.em.createQuery(hql);

        q.setParameter("productId", productId);

        List<Object[]> result =  (List<Object[]>) q.getResultList();

        Map<String, String> specNameValue = new HashMap<>();

        for(Object[] data: result) {

             specNameValue.put((String) data[0], (String) data[1]);
        }
        return specNameValue;
    }
}