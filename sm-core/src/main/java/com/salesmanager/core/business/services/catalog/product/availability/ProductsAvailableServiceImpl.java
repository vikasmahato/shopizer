package com.salesmanager.core.business.services.catalog.product.availability;

import com.salesmanager.core.business.repositories.catalog.product.availability.ProductsAvailableRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.availability.ProductsAvailable;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Service("productsAvailableService")
public class ProductsAvailableServiceImpl extends
        SalesManagerEntityServiceImpl<Long, ProductsAvailable> implements ProductsAvailableService {

    private ProductsAvailableRepository productsAvailableRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsAvailableServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Inject
    public ProductsAvailableServiceImpl(
            ProductsAvailableRepository productsAvailableRepository) {
        super(productsAvailableRepository);
        this.productsAvailableRepository = productsAvailableRepository;
    }

    @Override
    public ProductsAvailable getByProductVariant(Long productId, Long variantId) {
        return productsAvailableRepository.getByProductVariant(productId, variantId);
    }

    @Override
    public ProductsAvailable getByProduct(Long productId) {
        return productsAvailableRepository.getByProduct(productId);
    }

    @Override
    public Long getLastAvailId() {
        return productsAvailableRepository.getLastAvailId();
    }

    @Override
    public List<ProductsAvailable> getByVariants(List<Long> variants) {
        return productsAvailableRepository.getByVariants(variants);
    }

    @Override
    public Set<ProductsAvailable> getByAvailId(Long avail_id) {
        return productsAvailableRepository.getByAvailId(avail_id);
    }

    @Override
    public ProductsAvailable getByVariant(List<Long> variants)
    {

        ProductsAvailable available = new ProductsAvailable();
        Long a_id = productsAvailableRepository.getByVariant(variants);

        if (a_id != null)
        {
            Set<ProductsAvailable> availables = getByAvailId(a_id);
            if (availables.stream().findFirst().isPresent())
                available = availables.stream().findFirst().get();
        }

        return  available;
    }
}
