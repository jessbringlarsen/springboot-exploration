package dk.bringlarsen;

import java.util.Objects;
import java.util.Optional;

public class Configuration {

    public String getAccountId() {
        return getMandatoryProperty("AWS_ACCOUNT_ID");
    }

    public String getRegion() {
        return getMandatoryProperty("AWS_REGION");
    }

    public String getApplicationName() {
        return getMandatoryProperty("AWS_APPLICATION_NAME");
    }

    public String getEnvironmentName() {
        return getMandatoryProperty("APP_ENVIRONMENT_NAME");
    }

    public Optional<String> getSSLCertificateArn() {
        return getOptionalProperty("SSL_CERTIFICATE_ARN");
    }

    private String getMandatoryProperty(String name) {
        return getProperty(name, true);
    }

    private Optional<String> getOptionalProperty(String name) {
        return Optional.ofNullable(getProperty(name, false));
    }

    private String getProperty(String name, boolean mandatory) {
        String result = System.getenv(name);
        if (Objects.isNull(result) && mandatory) {
            throw new RuntimeException(name + " - property not defined!");
        }
        return result;
    }
}
