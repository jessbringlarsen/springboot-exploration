package dk.bringlarsen.application.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record TransactionDTO(UUID id,
                             UUID accountId,
                             String text,
                             BigDecimal amount,
                             @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssX") ZonedDateTime transactionDate) {
}
