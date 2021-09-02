package com.salesmanager.core.model.catalog.product.availability;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCTS_AVAILABLE",
        indexes = {@Index(name = "PRODUCT_AVAIL_ID", columnList = "PRODUCT_AVAIL_ID", unique = false)})
public class ProductsAvailable extends SalesManagerEntity<Long, ProductsAvailable> implements Auditable {

    private static final long serialVersionUID = 1L;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_AVAIL_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name = "PRODUCT_AVAIL_ID", nullable = false)
    private Long availId;

    @JsonIgnore
    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @JsonIgnore
    @ManyToOne(targetEntity = ProductSpecificationVariant.class)
    @JoinColumn(name = "VARIANT_ID")
    private ProductSpecificationVariant variant;

    @ManyToOne(targetEntity = ProductPrice.class)
    @JoinColumn(name = "PRICE_ID")
    private ProductPrice price;

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

    public Long getAvailId() {
        return availId;
    }

    public void setAvailId(Long avail_id) {
        this.availId = avail_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductSpecificationVariant getVariant() {
        return variant;
    }

    public void setVariant(ProductSpecificationVariant variant) {
        this.variant = variant;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public void setPrice(ProductPrice price) {
        this.price = price;
    }
}
