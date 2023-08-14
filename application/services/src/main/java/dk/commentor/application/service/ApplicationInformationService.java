package dk.bringlarsen.application.service;

public interface ApplicationInformationService {

    String getVersion();

    String getEnvironment();

    default String getTechnology() {
        return "Java";
    }
}
