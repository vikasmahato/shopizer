package com.salesmanager.core.business.services.catalog.product.vendor;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.vendor.Vendor;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;

import java.util.List;

public interface VendorService extends SalesManagerEntityService<Long, Vendor> {

    void saveOrUpdate(Vendor vendor) throws ServiceException;

    void delete(Vendor vendor) throws ServiceException;

    Vendor getByCode(String code);


    List<Vendor> list(MerchantStore store, Language language);

    Number getCountVendorAttachedProducts(Vendor delVendor) throws ServiceException;

    void deleteVendor(Vendor delVendor) throws ServiceException;
}
