package com.salesmanager.core.model.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@Table(name = "PRICE_ENQUIRY")
public class AskForPrice extends SalesManagerEntity<Long, com.salesmanager.core.model.store.AskForPrice> {


    /**
     *
     */
    private static final long serialVersionUID = 1172536723717691214L;


    @Id
    @Column(name = "ASK_FOR_PRICE_ID", unique=true, nullable=false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
            pkColumnValue = "ASK_FOR_PRICE_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;



    @NotEmpty
    @Column (name ="NAME", length=50)
    private String name;


    @NotEmpty
    @Min(0)
    @Column (name ="REQUIRED_QUANTITY")
    private Integer requiredQuantity;

    @NotEmpty
    @Pattern(regexp="^[a-zA-Z0-9_]*$")
    @Column(name = "SKU")
    private String sku;  //TODO: Add foreign key

    @NotEmpty
    @Column (name ="PRODUCT_NAME")
    private String productName;

    @NotEmpty
    @Column (name ="MOBILE_NO", length=13)
    private String mobileNo;


    @Column (name ="EMAIL", length=100)
    @Email
    private String email;

    @Column (name ="CITY", length=100)
    private String city;

    @Column (name ="DESCRIPTION")
    private String description;

    public AskForPrice() {
    }

    public AskForPrice(AskForPriceBuilder askForPriceBuilder) {
        this.id = askForPriceBuilder.id;
        this.name = askForPriceBuilder.name;
        this.requiredQuantity = askForPriceBuilder.requiredQuantity;
        this.sku = askForPriceBuilder.sku;
        this.productName = askForPriceBuilder.productName;
        this.mobileNo = askForPriceBuilder.mobileNo;
        this.email = askForPriceBuilder.email;
        this.city = askForPriceBuilder.city;
        this.description = askForPriceBuilder.description;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Integer requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class AskForPriceBuilder {
        private Long id;
        private String name;
        private Integer requiredQuantity;
        private String sku;
        private String productName;
        private String mobileNo;
        private String email;
        private String city;
        private String description;

        public AskForPriceBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public AskForPriceBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AskForPriceBuilder setRequiredQuantity(Integer requiredQuantity) {
            this.requiredQuantity = requiredQuantity;
            return this;
        }

        public AskForPriceBuilder setSku(String sku) {
            this.sku = sku;
            return this;
        }

        public AskForPriceBuilder setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public AskForPriceBuilder setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public AskForPriceBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AskForPriceBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public AskForPriceBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public AskForPrice build() {
            return new AskForPrice(this);
        }
    }
}
