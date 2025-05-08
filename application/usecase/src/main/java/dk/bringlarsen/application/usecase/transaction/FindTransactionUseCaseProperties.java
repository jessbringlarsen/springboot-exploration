package dk.bringlarsen.application.usecase.transaction;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "usecase.findtransaction")
public class FindTransactionUseCaseProperties {

    @Positive
    @NotNull
    private Integer maxpagesize;

    public Integer getMaxpagesize() {
        return maxpagesize;
    }
}
