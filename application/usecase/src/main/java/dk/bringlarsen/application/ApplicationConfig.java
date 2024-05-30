package dk.bringlarsen.application;

import dk.bringlarsen.application.usecase.transaction.FindTransactionUseCaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({FindTransactionUseCaseProperties.class})
public class ApplicationConfig {
}
