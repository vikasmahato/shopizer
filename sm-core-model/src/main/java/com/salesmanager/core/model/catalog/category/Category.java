package com.salesmanager.core.model.catalog.category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;

@Entity
@EntityListeners(value = com.salesmanager.core.model.common.audit.AuditListener.class)
@Table(name = "CATEGORY",uniqueConstraints=
    @UniqueConstraint(columnNames = {"MERCHANT_ID", "CODE"}) )


public class Category extends SalesManagerEntity<Long, Category> implements Auditable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "CATEGORY_ID", unique=true, nullable=false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CATEGORY_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Valid
    @OneToMany(mappedBy="category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CategoryDescription> descriptions = new HashSet<CategoryDescription>();

    @Valid
    @OneToMany(mappedBy="category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CategorySpecification> specifications = new HashSet<CategorySpecification>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MERCHANT_ID", nullable=false)
    private MerchantStore merchantStore;
    
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<Category> categories = new ArrayList<Category>();
    
    @Column(name = "CATEGORY_IMAGE", length=100)
    private String categoryImage;

    @Column(name = "SORT_ORDER")
    private Integer sortOrder = 0;

    @Column(name = "CATEGORY_STATUS")
    private boolean categoryStatus;

    @Column(name = "VISIBLE")
    private boolean visible;

    @Column(name = "DEPTH")
    private Integer depth;

    @Column(name = "LINEAGE")
    private String lineage;
    
    @Column(name="FEATURED")
    private boolean featured;
    
    @NotEmpty
    @Column(name="CODE", length=100, nullable=false)
    private String code;

    @OneToMany(mappedBy="category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductSpecificationVariant> productSpecificationVariant;

    public Set<ProductSpecificationVariant> getProductSpecificationVariant() {
        return productSpecificationVariant;
    }

    public void setProductSpecificationVariant(Set<ProductSpecificationVariant> productSpecificationVariant) {
        this.productSpecificationVariant = productSpecificationVariant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Category() {
    }
    
    public Category(MerchantStore store) {
        this.merchantStore = store;
        this.id = 0L;
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


    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public String getLineage() {
        return lineage;
    }

    public void setLineage(String lineage) {
        this.lineage = lineage;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
    



    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public CategoryDescription getDescription() {
        if(descriptions!=null && descriptions.size()>0) {
            return descriptions.iterator().next();
        }
        
        return null;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public Set<CategoryDescription> getDescriptions() {
      return descriptions;
    }

    public void setDescriptions(Set<CategoryDescription> descriptions) {
      this.descriptions = descriptions;
    }

    public Set<CategorySpecification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Set<CategorySpecification> specifications) {
        this.specifications = specifications;
    }
}