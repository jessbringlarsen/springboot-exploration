package dk.bringlarsen.application.resources.customer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CustomerInputDTO implements Serializable {

    @JsonProperty("CustomerName")
    private String name;

    public CustomerInputDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}
