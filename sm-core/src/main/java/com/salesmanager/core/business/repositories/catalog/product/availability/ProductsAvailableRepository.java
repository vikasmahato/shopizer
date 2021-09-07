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

//    @Query("SELECT P1.availId FROM (SELECT p1.availId, count(p1) FROM ProductsAvailable p1) P1 JOIN FETCH (SELECT p2.availId, count(p2) FROM ProductsAvailable p2 WHERE p2.variant.id IN (?1)) P2 ON P1.availId = P2.availId where P1.c1 = P2.c2")
//    ProductsAvailable getByVariant(List<Long> variants);

    @Query(value = "Select C.PRODUCT_AVAIL_ID from  (select PRODUCT_AVAIL_ID, count(*) AS c1 from SALESMANAGER.PRODUCTS_AVAILABLE group by PRODUCT_AVAIL_ID) AS C  JOIN ( Select PRODUCT_AVAIL_ID, count(*) AS c2 from SALESMANAGER.PRODUCTS_AVAILABLE  where VARIANT_ID in ( ?1 ) group by PRODUCT_AVAIL_ID) AS D on C.PRODUCT_AVAIL_ID = D.PRODUCT_AVAIL_ID where C.c1 = D.c2", nativeQuery = true)
    Long getByVariant(List<Long> variants);
}
