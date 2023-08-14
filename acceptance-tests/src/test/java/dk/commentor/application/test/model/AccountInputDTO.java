package dk.bringlarsen.application.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountInputDTO(String customerId, @JsonProperty("AccountName") String accountName) {
}
