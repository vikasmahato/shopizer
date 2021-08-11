package com.salesmanager.core.model.catalog.product.specification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategorySpecification;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.reference.language.Language;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="PRODUCT_SPECIFICATION",uniqueConstraints={
        @UniqueConstraint(columnNames={
                "CATEGORY_ID",
                "PRODUCT_ID",
                "SPECIFICATION_ID",
                "SPECIFICATION_VALUE",
                "LANGUAGE_ID"
        })
}
)
public class ProductSpecificationVariant extends SalesManagerEntity<Long, ProductSpecificationVariant> implements Auditable {

    private static final long serialVersionUID = 1L;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Id
    @Column(name = "ID", unique=true, nullable=false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SPECIFICATION_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @JsonIgnore
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToOne(targetEntity = CategorySpecification.class)
    @JoinColumn(name = "SPECIFICATION_ID", nullable = false)
    private CategorySpecification specification;

    @JsonIgnore
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LANGUAGE_ID", nullable=false)
    private Language language;

    @Column(name = "SPECIFICATION_VALUE")
    private String value;

    public Map<Long, ArrayList<String>> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<Long, ArrayList<String>> valueMap) {
        this.valueMap = valueMap;
    }

    @Transient
    private Map<Long, ArrayList<String>> valueMap = new HashMap<Long, ArrayList<String>>();

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public CategorySpecification getSpecification() {
        return specification;
    }

    public void setSpecification(CategorySpecification specification) {
        this.specification = specification;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
