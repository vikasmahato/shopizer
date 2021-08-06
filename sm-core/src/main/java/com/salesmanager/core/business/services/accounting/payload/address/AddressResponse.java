package com.salesmanager.core.business.services.accounting.payload.address;

public class AddressResponse {
    private String line1;
    private String line2;
    private String city;
    private String country;
    private String countrySubDivisionCode;
    private String postalCode;

    public AddressResponse() {
    }

    public AddressResponse(String line1, String city, String country, String countrySubDivisionCode, String postalCode) {
        this.line1 = line1;
        this.city = city;
        this.country = country;
        this.countrySubDivisionCode = countrySubDivisionCode;
        this.postalCode = postalCode;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountrySubDivisionCode() {
        return countrySubDivisionCode;
    }

    public void setCountrySubDivisionCode(String countrySubDivisionCode) {
        this.countrySubDivisionCode = countrySubDivisionCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
