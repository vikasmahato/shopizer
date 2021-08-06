package com.salesmanager.core.business.services.accounting.service;

import com.intuit.ipp.exception.FMSException;
import com.salesmanager.core.business.services.accounting.payload.customer.CustomerRequest;
import com.salesmanager.core.business.services.accounting.payload.customer.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest) throws FMSException;
    CustomerResponse updateCustomer(CustomerRequest customerRequest);
    Boolean deleteCustomer(Long customerId);
    CustomerResponse getCustomer(Long customerId);
    CustomerResponse getCustomer(String email);
    List<CustomerResponse> getAllCustomers();
}
