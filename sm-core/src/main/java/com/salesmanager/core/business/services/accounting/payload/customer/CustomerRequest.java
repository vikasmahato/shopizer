package com.salesmanager.core.business.services.accounting.payload.customer;

import com.salesmanager.core.business.services.accounting.payload.address.AddressRequest;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerRequest {

    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String companyName;
    private String primaryPhone;
    private AddressRequest billingAddress;
    private AddressRequest shippingAddress;
    private String email;
    private Boolean job;
    private BigDecimal balance;
    private Date openBalanceDate;
    private BigDecimal balanceWithJobs;
    private BigDecimal overDueBalance;
    private Boolean tdsEnabled;
    private String gstin;
    private String contactName;
    private String altContactName;
    private String notes;
    private Boolean billWithParent;
    private Integer level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public AddressRequest getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressRequest billingAddress) {
        this.billingAddress = billingAddress;
    }

    public AddressRequest getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressRequest shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getJob() {
        return job;
    }

    public void setJob(Boolean job) {
        this.job = job;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getOpenBalanceDate() {
        return openBalanceDate;
    }

    public void setOpenBalanceDate(Date openBalanceDate) {
        this.openBalanceDate = openBalanceDate;
    }

    public BigDecimal getBalanceWithJobs() {
        return balanceWithJobs;
    }

    public void setBalanceWithJobs(BigDecimal balanceWithJobs) {
        this.balanceWithJobs = balanceWithJobs;
    }

    public BigDecimal getOverDueBalance() {
        return overDueBalance;
    }

    public void setOverDueBalance(BigDecimal overDueBalance) {
        this.overDueBalance = overDueBalance;
    }

    public Boolean getTdsEnabled() {
        return tdsEnabled;
    }

    public void setTdsEnabled(Boolean tdsEnabled) {
        this.tdsEnabled = tdsEnabled;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getAltContactName() {
        return altContactName;
    }

    public void setAltContactName(String altContactName) {
        this.altContactName = altContactName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getBillWithParent() {
        return billWithParent;
    }

    public void setBillWithParent(Boolean billWithParent) {
        this.billWithParent = billWithParent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}