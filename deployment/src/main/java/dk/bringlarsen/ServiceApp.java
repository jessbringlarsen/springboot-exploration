package dk.bringlarsen;

import dev.stratospheric.cdk.ApplicationEnvironment;
import dev.stratospheric.cdk.Network;
import dev.stratospheric.cdk.Service;
import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.PolicyStatement;

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

        Stack serviceStack = new Stack(app, "ServiceStack", StackProps.builder()
            .stackName(String.format("%s-%s-%s", configuration.getApplicationName(), configuration.getEnvironmentName(), "service"))
            .tags(Collections.singletonMap("project", configuration.getApplicationName()))
            .env(awsEnvironment)
            .build());

        new Service(
            serviceStack,
            "Service",
            awsEnvironment,
            applicationEnvironment,
            new Service.ServiceInputParameters(
                new Service.DockerImageSource(configuration.getDockerRepositoryName(), configuration.getDockerImageTag()),
                environmentVariables(configuration.getSpringBootProfile(), configuration.getEnvironmentName()))
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

    static Map<String, String> environmentVariables(String springProfile, String environmentName) {
        Map<String, String> vars = new HashMap<>();
        vars.put("SPRING_PROFILES_ACTIVE", springProfile);
        vars.put("ENVIRONMENT_NAME", environmentName);

        return vars;
    }
}
