package dk.bringlarsen;

import java.util.Objects;

public class Configuration {

    public String getAccountId() {
        return getProperty("AWS_ACCOUNT_ID");
    }

    public String getRegion() {
        return getProperty("AWS_REGION");
    }

    public String getApplicationName() {
        return getProperty("AWS_APPLICATION_NAME");
    }

    private String getProperty(String name) {
        String result = System.getenv("AWS_APPLICATION_NAME");
        if (Objects.isNull(result)) {
            throw new RuntimeException(name + " - property not defined!");
        }
        return result;
    }
}
