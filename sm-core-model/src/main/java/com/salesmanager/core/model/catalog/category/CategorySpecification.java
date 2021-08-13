package com.salesmanager.core.model.catalog.category;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.reference.language.Language;

import java.util.Set;


@Entity
@Table(name="CATEGORY_SPECIFICATION",uniqueConstraints={
        @UniqueConstraint(columnNames={
                "CATEGORY_ID",
                "SPECIFICATION",
                "LANGUAGE_ID"
        })
}
)
public class CategorySpecification extends SalesManagerEntity<Long, CategorySpecification> implements Auditable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SPECIFICATION_ID", unique=true, nullable=false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SPECIFICATION_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @JsonIgnore
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @Column(name = "SPECIFICATION")
    private String specification;

    @Column(name="FILTER", columnDefinition="tinyint(1) default 1")
    private Boolean filter;

    @Column(name = "VARIANT")
    private Boolean variant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LANGUAGE_ID", nullable=false)
    private Language language;

    @OneToMany(mappedBy="specification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductSpecificationVariant> productSpecificationVariant;

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Boolean getVariant() {
        return variant;
    }

    public void setVariant(Boolean variant) {
        this.variant = variant;
    }

    public CategorySpecification() {
    }

    public CategorySpecification(String name, Language language) {
        this.setSpecification(name);
        this.setLanguage(language);
        this.setId(0L);
    }

    public Boolean getFilter() {
        return filter;
    }

    public void setFilter(Boolean filter) {
        this.filter = filter;
    }


    public Set<ProductSpecificationVariant> getProductSpecificationVariant() {
        return productSpecificationVariant;
    }

    public void setProductSpecificationVariant(Set<ProductSpecificationVariant> productSpecificationVariant) {
        this.productSpecificationVariant = productSpecificationVariant;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
