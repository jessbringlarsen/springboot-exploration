package dk.bringlarsen.application.resources.applicationinformation;

import dk.bringlarsen.application.resources.applicationinformation.mapper.ApplicationInformationMapper;
import dk.bringlarsen.application.resources.applicationinformation.model.ApplicationInformationDTO;
import dk.bringlarsen.application.usecase.applicationinfo.GetApplicationInformationUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationInformation {

    private final GetApplicationInformationUseCase usecase;
    private final ApplicationInformationMapper mapper;

    @Autowired
    public ApplicationInformation(GetApplicationInformationUseCase usecase, ApplicationInformationMapper mapper) {
        this.usecase = usecase;
        this.mapper = mapper;
    }

    @Operation(tags = {"API version"}, summary = "Provide application information")
    @GetMapping("/apiVersion")
    public ResponseEntity<ApplicationInformationDTO> get() {
        return ResponseEntity.ok(mapper.map(usecase.execute()));
    }
}
