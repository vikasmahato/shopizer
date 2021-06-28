package com.salesmanager.core.model.catalog.product.vendor;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(value = com.salesmanager.core.model.common.audit.AuditListener.class)
@Table(name = "VENDOR",uniqueConstraints=
@UniqueConstraint(columnNames = {"VENDOR_ID", "CODE"}) )
public class Vendor extends SalesManagerEntity<Long, Category> implements Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "VENDOR_ID", unique=true, nullable=false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "VENDOR_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Valid
    @OneToMany(mappedBy="vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<VendorDescription> descriptions = new HashSet<>();

    @Column(name = "SORT_ORDER")
    private Integer sortOrder = 0;

    @Column(name = "VENDOR_STATUS")
    private boolean categoryStatus;

    @Column(name = "VENDOR_NAME")
    @NotNull
    private String name;

    @Column(name = "VENDOR_EMAIL")
    @Email
    private String email;

    @Column(name = "VENDOR_PHONE")
    @Size(min=10,max=10)
    @NotNull
    private String phone;

    @NotEmpty
    @Column(name="CODE", length=100, nullable=false)
    private String code;

    public Vendor() {
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }


    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(boolean categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public VendorDescription getDescription() {
        if(descriptions!=null && descriptions.size()>0) {
            return descriptions.iterator().next();
        }
        return null;
    }

    public Set<VendorDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<VendorDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
