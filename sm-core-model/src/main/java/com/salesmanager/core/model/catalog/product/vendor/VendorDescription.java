package com.salesmanager.core.model.catalog.product.vendor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;
import com.salesmanager.core.model.reference.language.Language;

import javax.persistence.*;

@Entity
@Table(name="VENDOR_DESCRIPTION",uniqueConstraints={
        @UniqueConstraint(columnNames={
                "VENDOR_ID",
                "LANGUAGE_ID"
        })
}
)
@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "vendor_description_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)

public class VendorDescription extends Description {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne(targetEntity = Vendor.class)
    @JoinColumn(name = "VENDOR_ID", nullable = false)
    private Vendor vendor;

    @Column(name="SEF_URL", length=120)
    private String seUrl;

    @Column(name = "VENDOR_HIGHLIGHT")
    private String vendorHighlight;

    @Column(name="META_TITLE", length=120)
    private String metatagTitle;

    @Column(name="META_KEYWORDS")
    private String metatagKeywords;

    @Column(name="META_DESCRIPTION")
    private String metatagDescription;

    public VendorDescription() {
    }

    public VendorDescription(String name, Language language) {
        this.setName(name);
        this.setLanguage(language);
        super.setId(0L);
    }

    public String getUrl() {
        return seUrl;
    }

    public void setUrl(String seUrl) {
        this.seUrl = seUrl;
    }

    public String getMetatagTitle() {
        return metatagTitle;
    }

    public void setMetatagTitle(String metatagTitle) {
        this.metatagTitle = metatagTitle;
    }

    public String getMetatagKeywords() {
        return metatagKeywords;
    }

    public void setMetatagKeywords(String metatagKeywords) {
        this.metatagKeywords = metatagKeywords;
    }

    public String getMetatagDescription() {
        return metatagDescription;
    }

    public void setMetatagDescription(String metatagDescription) {
        this.metatagDescription = metatagDescription;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public String getVendorHighlight() {
        return vendorHighlight;
    }

    public void setVendorHighlight(String vendorHighlight) {
        this.vendorHighlight = vendorHighlight;
    }
}
