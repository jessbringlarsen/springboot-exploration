package dk.bringlarsen.application.resources.applicationinformation.mapper;

import dk.bringlarsen.application.resources.applicationinformation.model.ApplicationInformationDTO;
import dk.bringlarsen.application.usecase.applicationinfo.GetApplicationInformationUseCase.ApplicationInformation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ApplicationInformationMapper {

    ApplicationInformationDTO map(ApplicationInformation applicationInformation);
}
