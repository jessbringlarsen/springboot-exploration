package dk.bringlarsen.application.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record TransactionInputDTO(
    @JsonProperty("Text") String text,
    @JsonProperty("Amount") BigDecimal amount) {
}
