package com.salesmanager.core.business.repositories.catalog.product.vendor;

import com.salesmanager.core.model.catalog.product.vendor.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    @Query("select v from Vendor v left join fetch  v.descriptions vd where v.id=?1")
    Vendor findOne(Long id);

    @Query("select v from Vendor v left join v.descriptions vd where v.code=?1")
    Vendor getByCode(String code);

    @Query("select v from Vendor v left join fetch v.descriptions vd where vd.language.id=?1")
    List<Vendor> findByLanguage(Integer languageId);

    //@Query("select count(distinct p) from Product as p where p.vendors.id=?1")
    //Number countByProduct(Long vendorId);
}
