package com.salesmanager.core.business.services.catalog.product.availability;

import com.salesmanager.core.business.repositories.catalog.product.availability.ProductsAvailableRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.availability.ProductsAvailable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service("productsAvailableService")
public class ProductsAvailableServiceImpl extends
        SalesManagerEntityServiceImpl<Long, ProductsAvailable> implements ProductsAvailableService {

    private ProductsAvailableRepository productsAvailableRepository;

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
}
