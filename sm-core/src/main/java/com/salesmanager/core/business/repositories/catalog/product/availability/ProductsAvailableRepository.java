package com.salesmanager.core.business.repositories.catalog.product.availability;

import com.salesmanager.core.model.catalog.product.availability.ProductsAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductsAvailableRepository extends JpaRepository<ProductsAvailable, Long> {

    @Query(value = "SELECT MAX(product_avail_id) AS avail_id FROM SALESMANAGER.PRODUCTS_AVAILABLE", nativeQuery = true)
    Long getLastAvailId();

    @Query("SELECT p FROM ProductsAvailable p WHERE p.product.id = ?1 AND p.variant.id = ?2")
    ProductsAvailable getByProductVariant(Long productId, Long variantId);

    @Query("SELECT p FROM ProductsAvailable p WHERE p.product.id = ?1")
    ProductsAvailable getByProduct(Long productId);
}
