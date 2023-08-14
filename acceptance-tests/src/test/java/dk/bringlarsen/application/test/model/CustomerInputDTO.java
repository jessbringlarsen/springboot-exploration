package dk.bringlarsen.application.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerInputDTO {

    @JsonProperty("CustomerName")
    private String customerName;

    public CustomerInputDTO setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }
}
