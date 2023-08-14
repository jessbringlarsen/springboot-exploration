package dk.bringlarsen.application.resources.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountInputDTO(@JsonProperty("AccountName") String name) {
}
