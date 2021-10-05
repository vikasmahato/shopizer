package com.salesmanager.core.business.repositories.catalog.product.specification;

import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecificationVariant, Long> {

    @Query("SELECT s from ProductSpecificationVariant s where s.product.id = ?1 and s.specification.id = ?2 order by s.value asc")
    List<ProductSpecificationVariant> getBySpecificationId(Long productId, Long specificationId);

    @Query("SELECT s from ProductSpecificationVariant s where s.product.id = ?1 and s.specification.id = ?2 and s.value =?3")
    ProductSpecificationVariant getBySpecificationIdAndValue(Long productId, Long specificationId, String value);

//    @Query("SELECT s.value FROM ProductSpecificationVariant s join fetch ProductsAvailable a WHERE a.variant = s.id AND a.price = 105")
    @Query(value = "SELECT PRODUCT_SPECIFICATION.specification_value FROM `PRODUCT_SPECIFICATION`, PRODUCTS_AVAILABLE WHERE PRODUCTS_AVAILABLE.VARIANT_ID = PRODUCT_SPECIFICATION.ID AND PRODUCTS_AVAILABLE.PRICE_ID = ?1", nativeQuery = true)
    List<String> getVariantForPrice(Long priceId);
}
