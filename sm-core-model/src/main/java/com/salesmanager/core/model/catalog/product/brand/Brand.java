package com.salesmanager.core.model.catalog.product.brand;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "BRAND", uniqueConstraints=
@UniqueConstraint(columnNames = {"MERCHANT_ID", "CODE"}) )
public class Brand extends SalesManagerEntity<Long, Brand> implements Auditable {
	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_brand = "DEFAULT";
	
	@Id
	@Column(name = "BRAND_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "MANUFACT_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Embedded
	private AuditSection auditSection = new AuditSection();
	
	@OneToMany(mappedBy = "brand", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	private Set<BrandDescription> descriptions = new HashSet<BrandDescription>();
	
	@Column(name = "BRAND_IMAGE")
	private String image;
	
	@Column(name="SORT_ORDER")
	private Integer order = new Integer(0);

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="MERCHANT_ID", nullable=false)
	private MerchantStore merchantStore;
	
	@NotEmpty
	@Column(name="CODE", length=100, nullable=false)
	private String code;

	public Brand() {
	}

	@Override
	public Long getId() {
		return id;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<BrandDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Set<BrandDescription> descriptions) {
		this.descriptions = descriptions;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getOrder() {
		return order;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
