package com.salesmanager.core.business.services.accounting.quickbooks.customer;

import com.intuit.ipp.data.*;
import com.intuit.ipp.services.DataService;
import com.salesmanager.core.business.services.accounting.payload.address.AddressRequest;
import com.salesmanager.core.business.services.accounting.payload.customer.CustomerRequest;

import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.util.DateUtils;

import java.text.ParseException;
import java.util.List;

public class CustomerHelper {
    private CustomerHelper() {

    }

    public static Customer transformCustomerRequest(CustomerRequest customerRequest) {
        Customer customer = new Customer();

        customer.setDisplayName(customerRequest.getDisplayName());
        // customer.setTitle(RandomStringUtils.randomAlphanumeric(3));
        // customer.setGivenName(RandomStringUtils.randomAlphanumeric(6));
        // customer.setMiddleName(RandomStringUtils.randomAlphanumeric(6));
        // customer.setFamilyName(RandomStringUtils.randomAlphanumeric(6));

        // customer.setOrganization(false);
        // customer.setSuffix("Sr.");
        customer.setCompanyName(customerRequest.getCompanyName());
        customer.setActive(true);

        customer.setPrimaryPhone(getPrimaryPhone(customerRequest.getPrimaryPhone()));
        customer.setPrimaryEmailAddr(getEmailAddress(customerRequest.getEmail()));

        customer.setContactName(customerRequest.getContactName());
        customer.setAltContactName(customerRequest.getAltContactName());
        customer.setNotes(customerRequest.getNotes());
        //customer.setBalance(customerRequest.getBalance());
        //customer.setOpenBalanceDate(DateUtils.getCurrentDateTime());
        //customer.setBalanceWithJobs(new BigDecimal("5055.5"));
        //customer.setCreditLimit(new BigDecimal("200000"));
        //customer.setAcctNum("Test020102");
        //customer.setResaleNum("40");
        customer.setJob(customerRequest.getJob());

        customer.setBillAddr(getPhysicalAddress(customerRequest.getBillingAddress()));
        customer.setShipAddr(getPhysicalAddress(customerRequest.getShippingAddress()));

        return customer;
    }

    public static Customer getCustomer(DataService service) throws FMSException, ParseException {
        List<Customer> customers = (List<Customer>) service.findAll(new Customer());
        if (!customers.isEmpty()) {
            return customers.get(0);
        }
        return null;
    }

    public static ReferenceType getCustomerRef(Customer customer) {
        ReferenceType customerRef = new ReferenceType();
        customerRef.setName(customer.getDisplayName());
        customerRef.setValue(customer.getId());
        return customerRef;
    }

    private static PhysicalAddress getPhysicalAddress(AddressRequest addressRequest) {
        PhysicalAddress physicalAddress = new PhysicalAddress();
        physicalAddress.setLine1(addressRequest.getLine1());
        physicalAddress.setCity(addressRequest.getCity());
        physicalAddress.setCountry(addressRequest.getCountry());
        physicalAddress.setCountrySubDivisionCode(addressRequest.getCountrySubDivisionCode());
        physicalAddress.setPostalCode(addressRequest.getPostalCode());
        return physicalAddress;
    }

    private static WebSiteAddress getWebSiteAddress() {
        WebSiteAddress webSite = new WebSiteAddress();
        webSite.setURI("http://abccorp.com");
        webSite.setDefault(true);
        webSite.setTag("Business");
        return webSite;
    }

    private static PhysicalAddress getAddressForAST() {
        PhysicalAddress billingAdd = new PhysicalAddress();
        billingAdd.setLine1("2700 Coast Ave");
        billingAdd.setLine2("MountainView, CA 94043");
        return billingAdd;
    }

    private static JobInfo getJobInfo() throws ParseException {
        JobInfo jobInfo = new JobInfo();
        jobInfo.setDescription("In Progress");
        jobInfo.setStatus(JobStatusEnum.IN_PROGRESS);
        jobInfo.setStartDate(DateUtils.getDateWithPrevDays(2));
        jobInfo.setEndDate(DateUtils.getDateWithNextDays(5));
        jobInfo.setProjectedEndDate(DateUtils.getDateWithNextDays(5));
        return jobInfo;
    }

    private static TelephoneNumber getPrimaryPhone(String phoneNumber) {
        TelephoneNumber primaryNum = new TelephoneNumber();
        primaryNum.setFreeFormNumber(phoneNumber);
        primaryNum.setDefault(true);
        //primaryNum.setTag("Business");
        return primaryNum;
    }

    private static EmailAddress getEmailAddress(String email) {
        EmailAddress emailAddr = new EmailAddress();
        emailAddr.setAddress(email);
        return emailAddr;
    }
}
