package dk.bringlarsen.application.resources.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record TransactionInputDTO(
    @JsonProperty("Text") String text,
    @JsonProperty("Amount") BigDecimal amount) {
}
