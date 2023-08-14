package dk.bringlarsen.application.resources.applicationinformation.mapper;

import dk.bringlarsen.application.resources.applicationinformation.model.ApplicationInformationDTO;
import dk.bringlarsen.application.usecase.applicationinfo.GetApplicationInformationUseCase.ApplicationInformation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationInformationMapperTest {

    private final ApplicationInformationMapper mapper = new ApplicationInformationMapperImpl();

    @Test
    void dtoMapperTest() {
        ApplicationInformation from = new ApplicationInformation("Java", "1.0", "production");

        ApplicationInformationDTO to = mapper.map(from);

        assertEquals(from.environment(), to.getEnvironment());
        assertEquals(from.technology(), to.getTechnology());
        assertEquals(from.version(), to.getVersion());
    }
}
