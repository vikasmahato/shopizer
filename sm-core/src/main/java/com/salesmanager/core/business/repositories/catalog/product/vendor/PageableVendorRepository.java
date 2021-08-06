package com.salesmanager.core.business.repositories.catalog.product.vendor;

import com.salesmanager.core.model.catalog.product.vendor.Vendor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PageableVendorRepository extends PagingAndSortingRepository<Vendor, Long> {
}
