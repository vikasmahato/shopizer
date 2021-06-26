package com.salesmanager.core.business.services.accounting.payload.customer;

import com.salesmanager.core.business.services.accounting.payload.address.AddressResponse;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String companyName;
    private String primaryPhone;
    private AddressResponse billingAddress;
    private AddressResponse shippingAddress;
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

    public CustomerResponse() {
    }

    private CustomerResponse(CustomerResponsseBuilder customerResponsseBuilder) {
        this.id = customerResponsseBuilder.id;
        this.firstName = customerResponsseBuilder.firstName;
        this.lastName = customerResponsseBuilder.lastName;
        this.displayName = customerResponsseBuilder.displayName;
        this.companyName = customerResponsseBuilder.companyName;
        this.primaryPhone = customerResponsseBuilder.primaryPhone;
        this.billingAddress = customerResponsseBuilder.billingAddress;
        this.shippingAddress = customerResponsseBuilder.shippingAddress;
        this.email = customerResponsseBuilder.email;
        this.job = customerResponsseBuilder.job;
        this.balance = customerResponsseBuilder.balance;
        this.openBalanceDate = customerResponsseBuilder.openBalanceDate;
        this.balanceWithJobs = customerResponsseBuilder.balanceWithJobs;
        this.overDueBalance = customerResponsseBuilder.overDueBalance;
        this.tdsEnabled = customerResponsseBuilder.tdsEnabled;
        this.gstin = customerResponsseBuilder.gstin;
        this.contactName = customerResponsseBuilder.contactName;
        this.altContactName = customerResponsseBuilder.altContactName;
        this.notes = customerResponsseBuilder.notes;
        this.billWithParent = customerResponsseBuilder.billWithParent;
        this.level = customerResponsseBuilder.level;
    }

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

    public AddressResponse getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressResponse billingAddress) {
        this.billingAddress = billingAddress;
    }

    public AddressResponse getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressResponse shippingAddress) {
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

    public static class CustomerResponsseBuilder {
        private String id;
        private String firstName;
        private String lastName;
        private String displayName;
        private String companyName;
        private String primaryPhone;
        private AddressResponse billingAddress;
        private AddressResponse shippingAddress;
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

        public CustomerResponsseBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public CustomerResponsseBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public CustomerResponsseBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public CustomerResponsseBuilder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public CustomerResponsseBuilder setCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public CustomerResponsseBuilder setPrimaryPhone(String primaryPhone) {
            this.primaryPhone = primaryPhone;
            return this;
        }

        public CustomerResponsseBuilder setBillingAddress(AddressResponse billingAddress) {
            this.billingAddress = billingAddress;
            return this;
        }

        public CustomerResponsseBuilder setShippingAddress(AddressResponse shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public CustomerResponsseBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public CustomerResponsseBuilder setJob(Boolean job) {
            job = job;
            return this;
        }

        public CustomerResponsseBuilder setBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public CustomerResponsseBuilder setOpenBalanceDate(Date openBalanceDate) {
            this.openBalanceDate = openBalanceDate;
            return this;
        }

        public CustomerResponsseBuilder setBalanceWithJobs(BigDecimal balanceWithJobs) {
            this.balanceWithJobs = balanceWithJobs;
            return this;
        }

        public CustomerResponsseBuilder setOverDueBalance(BigDecimal overDueBalance) {
            this.overDueBalance = overDueBalance;
            return this;
        }

        public CustomerResponsseBuilder setTdsEnabled(Boolean tdsEnabled) {
            this.tdsEnabled = tdsEnabled;
            return this;
        }

        public CustomerResponsseBuilder setGstin(String gstin) {
            this.gstin = gstin;
            return this;
        }

        public CustomerResponsseBuilder setContactName(String contactName) {
            this.contactName = contactName;
            return this;
        }

        public CustomerResponsseBuilder setAltContactName(String altContactName) {
            this.altContactName = altContactName;
            return this;
        }

        public CustomerResponsseBuilder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public CustomerResponsseBuilder setBillWithParent(Boolean billWithParent) {
            this.billWithParent = billWithParent;
            return this;
        }

        public CustomerResponsseBuilder setLevel(Integer level) {
            this.level = level;
            return this;
        }

        public CustomerResponse build() {
            return new CustomerResponse(this);
        }
    }
}
