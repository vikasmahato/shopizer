package com.salesmanager.core.business.repositories.catalog.category;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategorySpecification;
import com.salesmanager.core.model.reference.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategorySpecificationRepository extends JpaRepository<CategorySpecification, Long> {

    @Query(value = "SELECT CATEGORY_SPECIFICATION.* FROM CATEGORY_SPECIFICATION, PRODUCT_CATEGORY WHERE CATEGORY_SPECIFICATION.CATEGORY_ID = PRODUCT_CATEGORY.CATEGORY_ID AND PRODUCT_CATEGORY.PRODUCT_ID = ?1 AND CATEGORY_SPECIFICATION.LANGUAGE_ID = ?2 ORDER BY FILTER ASC, VARIANT ASC", nativeQuery = true)
    List<CategorySpecification> specificationsForProduct(Long productId, Integer languageId);
}
