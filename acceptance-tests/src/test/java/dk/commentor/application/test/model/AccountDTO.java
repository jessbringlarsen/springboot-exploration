package dk.bringlarsen.application.test.model;

import java.math.BigDecimal;

public record AccountDTO(String id, String customerId, String name, BigDecimal balance) {
}
