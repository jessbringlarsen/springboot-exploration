package dk.bringlarsen.application.domain.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record Transaction(UUID id, UUID accountId, String text, BigDecimal amount, ZonedDateTime transactionDate) {

}
