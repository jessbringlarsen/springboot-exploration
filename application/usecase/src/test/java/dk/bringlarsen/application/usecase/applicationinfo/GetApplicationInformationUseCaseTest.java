package dk.bringlarsen.application.usecase.applicationinfo;

import dk.bringlarsen.application.service.ApplicationInformationService;
import dk.bringlarsen.application.usecase.applicationinfo.GetApplicationInformationUseCase.ApplicationInformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicationInformationUseCaseTest {

    @Mock
    ApplicationInformationService service;
    @InjectMocks
    GetApplicationInformationUseCase useCase;

    @Test
    void testGetEnvironmentApplicationInformation() {
        when(service.getEnvironment()).thenReturn("production");

        ApplicationInformation applicationInformation = useCase.execute();

        assertEquals("production", applicationInformation.environment());
    }

    @Test
    void testGetApplicationInformation() {
        when(service.getVersion()).thenReturn("1.0.0");

        ApplicationInformation applicationInformation = useCase.execute();

        assertEquals("1.0.0", applicationInformation.version());
    }

    @Test
    void testGetTechnologyApplicationInformation() {
        when(service.getTechnology()).thenReturn("Java");

        ApplicationInformation applicationInformation = useCase.execute();

        assertEquals("Java", applicationInformation.technology());
    }
}
