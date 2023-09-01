package dk.bringlarsen;

import dev.stratospheric.cdk.ApplicationEnvironment;
import dev.stratospheric.cdk.Network;
import dev.stratospheric.cdk.PostgresDatabase;
import dev.stratospheric.cdk.Service;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.secretsmanager.ISecret;
import software.amazon.awscdk.services.secretsmanager.Secret;
import software.constructs.Construct;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class ServiceApp {

    public static void main(final String[] args) {
        Configuration configuration = new Configuration();
        App app = new App();

        Environment awsEnvironment = Environment.builder()
            .account(configuration.getAccountId())
            .region(configuration.getRegion())
            .build();

        ApplicationEnvironment applicationEnvironment = new ApplicationEnvironment(
            configuration.getApplicationName(),
            configuration.getEnvironmentName()
        );

        // This stack is just a container for the parameters below, because they need a Stack as a scope.
        // We're making this parameters stack unique with each deployment by adding a timestamp, because updating an existing
        // parameters stack will fail because the parameters may be used by an old service stack.
        // This means that each update will generate a new parameters stack that needs to be cleaned up manually!
        long timestamp = System.currentTimeMillis();
        Stack parametersStack = new Stack(app, "ServiceParameters-" + timestamp, StackProps.builder()
            .stackName(String.format("%s-%s-%s-%s", configuration.getApplicationName(), configuration.getEnvironmentName(), "parameters", timestamp))
            .env(awsEnvironment)
            .build());

        PostgresDatabase.DatabaseOutputParameters databaseOutputParameters =
            PostgresDatabase.getOutputParametersFromParameterStore(parametersStack, applicationEnvironment);

        List<String> securityGroupIdsToGrantIngressFromEcs = Collections.singletonList(databaseOutputParameters.getDatabaseSecurityGroupId());

        Stack serviceStack = new Stack(app, "ServiceStack", StackProps.builder()
            .stackName(String.format("%s-%s-%s", configuration.getApplicationName(), configuration.getEnvironmentName(), "service"))
            .tags(Collections.singletonMap("project", configuration.getApplicationName()))
            .env(awsEnvironment)
            .build());

        Map<String, String> environmentVariables = environmentVariables(serviceStack,
            configuration.getSpringBootProfile(),
            configuration.getEnvironmentName(),
            databaseOutputParameters);

        new Service(
            serviceStack,
            "Service",
            awsEnvironment,
            applicationEnvironment,
            new Service.ServiceInputParameters(
                new Service.DockerImageSource(configuration.getDockerRepositoryName(), configuration.getDockerImageTag()),
                securityGroupIdsToGrantIngressFromEcs,
                environmentVariables)
                .withCpu(256)
                .withMemory(1024)
                .withTaskRolePolicyStatements(List.of(
                    PolicyStatement.Builder.create()
                        .sid("AllowSendingMetricsToCloudWatch")
                        .effect(Effect.ALLOW)
                        .resources(singletonList("*")) // CloudWatch does not have any resource-level permissions, see https://stackoverflow.com/a/38055068/9085273
                        .actions(singletonList("cloudwatch:PutMetricData"))
                        .build()
                ))
                .withStickySessionsEnabled(true)
                .withHealthCheckPath("/actuator/health")
                .withAwsLogsDateTimeFormat("%Y-%m-%dT%H:%M:%S.%f%z")
                .withHealthCheckIntervalSeconds(30), // needs to be long enough to allow for slow start up with low-end computing instances

            Network.getOutputParametersFromParameterStore(serviceStack, applicationEnvironment.getEnvironmentName()));

        app.synth();
    }

    static Map<String, String> environmentVariables(Construct scope,
                                                    String springProfile,
                                                    String environmentName,
                                                    PostgresDatabase.DatabaseOutputParameters databaseOutputParameters) {
        Map<String, String> vars = new HashMap<>();
        ISecret databaseSecret = Secret.fromSecretCompleteArn(scope, "databaseSecret", databaseOutputParameters.getDatabaseSecretArn());

        vars.put("SPRING_DATASOURCE_URL",
            String.format("jdbc:postgresql://%s:%s/%s",
                databaseOutputParameters.getEndpointAddress(),
                databaseOutputParameters.getEndpointPort(),
                databaseOutputParameters.getDbName()));
        vars.put("SPRING_DATASOURCE_USERNAME", databaseSecret.secretValueFromJson("username").toString());
        vars.put("SPRING_DATASOURCE_PASSWORD", databaseSecret.secretValueFromJson("password").toString());

        vars.put("SPRING_PROFILES_ACTIVE", springProfile);
        vars.put("ENVIRONMENT_NAME", environmentName);

        return vars;
    }
}
