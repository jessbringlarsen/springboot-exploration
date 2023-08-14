package dk.bringlarsen.application.usecase.applicationinfo;

import dk.bringlarsen.application.service.ApplicationInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetApplicationInformationUseCase {

    private final ApplicationInformationService service;

    @Autowired
    public GetApplicationInformationUseCase(ApplicationInformationService service) {
        this.service = service;
    }

    public ApplicationInformation execute() {
        return new ApplicationInformation(service.getTechnology(),
            service.getVersion(),
            service.getEnvironment());
    }

    public record ApplicationInformation(String technology, String version, String environment) {

    }
}
