package dk.bringlarsen;


import dev.stratospheric.cdk.ApplicationEnvironment;
import dev.stratospheric.cdk.PostgresDatabase;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class DatabaseApp {

    public static void main(final String[] args) {
        Configuration configuration = new Configuration();
        App app = new App();

        Environment awsEnvironment = Environment.builder()
            .account(configuration.getAccountId())
            .region(configuration.getRegion())
            .build();

        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment(
            configuration.getApplicationName(),
            configuration.getEnvironmentName());

        Stack databaseStack = new Stack(app, "DatabaseStack", StackProps.builder()
            .stackName(String.format("%s-%s-%s", configuration.getApplicationName(), configuration.getEnvironmentName(), "database"))
            .env(awsEnvironment)
            .build());

        PostgresDatabase.DatabaseInputParameters databaseInputParameters = new PostgresDatabase.DatabaseInputParameters()
            .withPostgresVersion("15.4")
            .withInstanceClass("db.t3.micro");

        new PostgresDatabase(
            databaseStack,
            "Database",
            awsEnvironment,
            applicationEnvironment,
            databaseInputParameters);

        app.synth();
    }
}
