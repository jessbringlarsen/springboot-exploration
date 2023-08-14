package dk.bringlarsen.application.infrastructure.applicationinformation;

import dk.bringlarsen.application.service.ApplicationInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInformationServiceSpringImpl implements ApplicationInformationService {

    private final ConfigurableEnvironment environment;
    private final BuildProperties buildProperties;

    @Autowired
    public ApplicationInformationServiceSpringImpl(ConfigurableEnvironment environment, BuildProperties buildProperties) {
        this.environment = environment;
        this.buildProperties = buildProperties;
    }

    @Override
    public String getEnvironment() {
        String[] activeProfiles = environment.getActiveProfiles();
        if(activeProfiles.length == 0) {
            return "production";
        }
        return String.join(", ", activeProfiles);
    }

    @Override
    public String getVersion() {
        return buildProperties.getVersion();
    }
}
