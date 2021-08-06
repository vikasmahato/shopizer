package com.salesmanager.shop.admin.model.catalog;

import com.salesmanager.core.model.catalog.product.vendor.VendorDescription;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vendor implements Serializable {

    private static final long serialVersionUID = -4531526676134574984L;

    /**
     *
     */

    //provides wrapping to the main Vendor entity
    private com.salesmanager.core.model.catalog.product.vendor.Vendor vendor;

    @Valid
    private List<VendorDescription> descriptions = new ArrayList<>();

    private Integer order = 0;

    @NotNull
    private String code;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 10, max = 10)
    private String phone;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public com.salesmanager.core.model.catalog.product.vendor.Vendor getVendor() {
        return vendor;
    }

    public List<VendorDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<VendorDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setVendor(com.salesmanager.core.model.catalog.product.vendor.Vendor vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
