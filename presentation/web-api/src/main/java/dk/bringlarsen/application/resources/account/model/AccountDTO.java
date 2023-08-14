package dk.bringlarsen.application.resources.account.model;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountDTO(UUID id, UUID customerId, String name, BigDecimal balance) {
}
