package com.salesmanager.core.business.repositories.catalog.product.specification;

import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecificationVariant, Long> {

    @Query("SELECT s from ProductSpecificationVariant s where s.product.id = ?1 and s.specification.id = ?2 order by s.value asc")
    List<ProductSpecificationVariant> getBySpecificationId(Long productId, Long specificationId);

    @Query("SELECT s from ProductSpecificationVariant s where s.product.id = ?1 and s.specification.id = ?2 and s.value =?3")
    ProductSpecificationVariant getBySpecificationIdAndValue(Long productId, Long specificationId, String value);

}
