package com.balaji.springjwt.payload.request;

public class CustomerAddRequest {

    private String customerName;
    private String fullAddress;
    private String phone;

    // Constructors
    public CustomerAddRequest() {
    }

    public CustomerAddRequest(String customerName, String fullAddress, String phone) {
        this.customerName = customerName;
        this.fullAddress = fullAddress;
        this.phone = phone;
    }

    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CustomerRequest{" +
                "customerName='" + customerName + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
