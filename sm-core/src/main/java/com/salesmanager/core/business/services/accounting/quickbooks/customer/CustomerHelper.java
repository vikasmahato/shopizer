package com.salesmanager.core.business.services.accounting.quickbooks.customer;

import com.intuit.ipp.data.*;
import com.intuit.ipp.services.DataService;
import com.salesmanager.core.business.services.accounting.payload.address.AddressRequest;

import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.util.DateUtils;

import java.text.ParseException;
import java.util.List;

public class CustomerHelper {
    private CustomerHelper() {

    }

    public static Customer transformCustomerRequest(com.salesmanager.core.model.customer.Customer customer) {
        Customer qbCustomer = new Customer();

        qbCustomer.setDisplayName(customer.getBilling().getCompany());
        // customer.setTitle(RandomStringUtils.randomAlphanumeric(3));
        // customer.setGivenName(RandomStringUtils.randomAlphanumeric(6));
        // customer.setMiddleName(RandomStringUtils.randomAlphanumeric(6));
        // customer.setFamilyName(RandomStringUtils.randomAlphanumeric(6));

        // customer.setOrganization(false);
        // customer.setSuffix("Sr.");
        qbCustomer.setCompanyName(customer.getBilling().getCompany());
        qbCustomer.setActive(true);

//        qbCustomer.setPrimaryPhone(getPrimaryPhone(customer.getPrimaryPhone()));
        qbCustomer.setPrimaryEmailAddr(getEmailAddress(customer.getEmailAddress()));

//        qbCustomer.setContactName(customer.getContactName());
//        qbCustomer.setAltContactName(customer.getAltContactName());
//        qbCustomer.setNotes(customer.getNotes());
        //customer.setBalance(customerRequest.getBalance());
        //customer.setOpenBalanceDate(DateUtils.getCurrentDateTime());
        //customer.setBalanceWithJobs(new BigDecimal("5055.5"));
        //customer.setCreditLimit(new BigDecimal("200000"));
        //customer.setAcctNum("Test020102");
        //customer.setResaleNum("40");
        qbCustomer.setJob(false);

//        qbCustomer.setBillAddr(getPhysicalAddress(customer.getBilling()));
//        qbCustomer.setShipAddr(getPhysicalAddress(customer.getDelivery()));

        qbCustomer.setGSTIN(customer.getGstin());

        return qbCustomer;
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

    public static com.salesmanager.core.model.customer.Customer getCustomerResponse(Customer qbCustomer)
    {
        com.salesmanager.core.model.customer.Customer customer = new com.salesmanager.core.model.customer.Customer();
        customer.setQuickbooks_id(Integer.valueOf(qbCustomer.getId()));
        return  customer;
    }
}
