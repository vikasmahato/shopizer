package com.salesmanager.core.business.repositories.catalog.product.specification;

import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecificationVariant, Long> {

}
