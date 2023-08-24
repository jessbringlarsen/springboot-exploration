package dk.bringlarsen;

import dev.stratospheric.cdk.DockerRepository;
import dev.stratospheric.cdk.DockerRepository.DockerRepositoryInputParameters;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class DockerRepositoryApp {
    public static void main(final String[] args) {
        Configuration configuration = new Configuration();
        App app = new App();
        Environment awsEnvironment =
            Environment.builder()
                .account(configuration.getAccountId())
                .region(configuration.getRegion())
                .build();
        Stack dockerRepositoryStack = new Stack(app, "DockerRepositoryApp",
            StackProps.builder()
                .stackName(configuration.getApplicationName())
                .env(awsEnvironment)
                .build());

        new DockerRepository(dockerRepositoryStack, "DockerRepository", awsEnvironment,
            new DockerRepositoryInputParameters(configuration.getApplicationName() + "-repo", configuration.getAccountId()));

        app.synth();
    }
}
