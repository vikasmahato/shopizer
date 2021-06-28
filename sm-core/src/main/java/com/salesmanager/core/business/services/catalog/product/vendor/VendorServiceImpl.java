package com.salesmanager.core.business.services.catalog.product.vendor;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.manufacturer.ManufacturerRepository;
import com.salesmanager.core.business.repositories.catalog.product.manufacturer.PageableManufacturerRepository;
import com.salesmanager.core.business.repositories.catalog.product.vendor.PageableVendorRepository;
import com.salesmanager.core.business.repositories.catalog.product.vendor.VendorRepository;
import com.salesmanager.core.business.services.catalog.product.manufacturer.ManufacturerServiceImpl;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.catalog.product.vendor.Vendor;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("vendorService")
public class VendorServiceImpl extends SalesManagerEntityServiceImpl<Long, Vendor>  implements VendorService{

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Inject
    private PageableVendorRepository pageableVendorRepository;

    private VendorRepository vendorRepository;


    @Inject
    public VendorServiceImpl(VendorRepository vendorRepository) {
        super(vendorRepository);
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void saveOrUpdate(Vendor vendor) throws ServiceException {
        LOGGER.debug("Creating Vendor");
        if (vendor.getId() != null && vendor.getId() > 0) {
            super.update(vendor);
        } else {
            super.create(vendor);
        }
    }

    @Override
    public void delete(Vendor vendor) throws ServiceException {
        vendor = this.getById(vendor.getId());
        super.delete(vendor);
    }

    @Override
    public Vendor getById(Long id) {
        return vendorRepository.findOne(id);
    }

    @Override
    public List<Vendor> list() {
        return null;
    }

    @Override
    public Long count() {
        return vendorRepository.count();
    }

    @Override
    public Vendor getByCode(String code) {
        return vendorRepository.getByCode(code);
    }

    @Override
    public List<Vendor> list(MerchantStore store, Language language) {
        return vendorRepository.findByLanguage(language.getId());
    }

    @Override
    public Number getCountVendorAttachedProducts(Vendor vendor) {
        // TODO: this won't let us delete vendors. fix query first.
        return 1;//vendorRepository.countByProduct(vendor.getId());
    }
}
