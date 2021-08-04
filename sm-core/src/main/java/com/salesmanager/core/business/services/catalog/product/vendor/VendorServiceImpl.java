package com.salesmanager.core.business.services.catalog.product.vendor;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.vendor.PageableVendorRepository;
import com.salesmanager.core.business.repositories.catalog.product.vendor.VendorRepository;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.vendor.Vendor;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service("vendorService")
public class VendorServiceImpl extends SalesManagerEntityServiceImpl<Long, Vendor>  implements VendorService{

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Inject
    private PageableVendorRepository pageableVendorRepository;

    private VendorRepository vendorRepository;

    @Inject
    private ProductService productService;


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
    public Number getCountVendorAttachedProducts(Vendor vendor) throws ServiceException {
        Language language = new Language();
        language.setId(1);
        List<Vendor> vendors = this.list((new MerchantStore()), language);
        Vendor dbVendor = getById(vendor.getId());
        int count =0;

        if (dbVendor != null && dbVendor.getId().longValue() == vendor.getId().longValue()) {
            vendors.add(dbVendor);

            Collections.reverse(vendors);

            List<Long> vendorIds = new ArrayList<Long>();

            for (Vendor c : vendors) {
                vendorIds.add(c.getId());
            }
            List<Product> products = productService.getProductsByVendor(vendorIds);
            for (Product product : products) {
                Product dbProduct = productService.getById(product.getId());
                Set<Vendor> productVendors = dbProduct.getVendors();
                if (productVendors.size() > 1) {
                    count = productVendors.size();
                    break;
                }
            }
        }
        return count;
    }

    @Override
    public void deleteVendor(Vendor vendor) throws ServiceException {
        Language language = new Language();
        language.setId(1);
        List<Vendor> vendors = this.list((new MerchantStore()), language);

        Vendor dbVendor = getById(vendor.getId());

        if (dbVendor != null && dbVendor.getId().longValue() == vendor.getId().longValue()) {

            vendors.add(dbVendor);

            Collections.reverse(vendors);

            List<Long> vendorIds = new ArrayList<Long>();

            for (Vendor c : vendors) {
                vendorIds.add(c.getId());
            }

            List<Product> products = productService.getProductsByVendor(vendorIds);
            // org.hibernate.Session session =
            // em.unwrap(org.hibernate.Session.class);// need to refresh the
            // session to update
            // all product
            // categories

            for (Product product : products) {
                // session.evict(product);// refresh product so we get all
                // product categories
                Product dbProduct = productService.getById(product.getId());
                Set<Vendor> productVendors = dbProduct.getVendors();
                if (productVendors.size() > 1) {
                    for (Vendor c : vendors) {
                        productVendors.remove(c);
                        productService.update(dbProduct);
                    }

                    if (product.getVendors() == null || product.getVendors().size() == 0) {
                        productService.delete(dbProduct);
                    }

                } else {
                    productService.delete(dbProduct);
                }

            }

            delete(vendor);
        }
    }
}
