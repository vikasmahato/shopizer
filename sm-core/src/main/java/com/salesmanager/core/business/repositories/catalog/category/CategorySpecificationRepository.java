package com.salesmanager.core.business.repositories.catalog.category;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategorySpecification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorySpecificationRepository extends JpaRepository<CategorySpecification, Long> {
}
