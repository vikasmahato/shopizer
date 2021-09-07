package com.salesmanager.core.business.repositories.catalog.product.availability;

import com.salesmanager.core.model.catalog.product.availability.ProductsAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProductsAvailableRepository extends JpaRepository<ProductsAvailable, Long> {

    @Query(value = "SELECT MAX(product_avail_id) AS avail_id FROM SALESMANAGER.PRODUCTS_AVAILABLE", nativeQuery = true)
    Long getLastAvailId();

    @Query("SELECT p FROM ProductsAvailable p WHERE p.product.id = ?1 AND p.variant.id = ?2")
    ProductsAvailable getByProductVariant(Long productId, Long variantId);

    @Query("SELECT p FROM ProductsAvailable p WHERE p.product.id = ?1")
    ProductsAvailable getByProduct(Long productId);

    @Query("SELECT p FROM ProductsAvailable p WHERE p.variant.id IN (?1)")
    List<ProductsAvailable> getByVariants(List<Long> variants);

    @Query("SELECT p FROM ProductsAvailable p WHERE p.availId = ?1")
    Set<ProductsAvailable> getByAvailId(Long avail_id);
}
