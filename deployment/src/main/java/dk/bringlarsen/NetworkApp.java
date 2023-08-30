package dk.bringlarsen;

import dev.stratospheric.cdk.Network;
import dev.stratospheric.cdk.Network.NetworkInputParameters;
import software.amazon.awscdk.*;

import java.util.Collections;
import java.util.Optional;

public class NetworkApp {

    public static void main(final String[] args) {
        Configuration configuration = new Configuration();
        App app = new App();

        Environment awsEnvironment = Environment.builder()
            .account(configuration.getAccountId())
            .region(configuration.getRegion())
            .build();

        Stack networkStack = new Stack(app, "NetworkStack", StackProps.builder()
            .stackName(String.format("%s-%s-%s", configuration.getApplicationName(), configuration.getEnvironmentName(), "network"))
            .tags(Collections.singletonMap("project", configuration.getApplicationName()))
            .env(awsEnvironment)
            .build());

        NetworkInputParameters inputParameters = new NetworkInputParameters();

        Optional<String> sslCertificateArn = configuration.getSSLCertificateArn();
        sslCertificateArn.ifPresent(inputParameters::withSslCertificateArn);

        Network network = new Network(
            networkStack,
            "Network",
            awsEnvironment,
            configuration.getEnvironmentName(),
            inputParameters);


        new CfnOutput(networkStack, "loadBalancerDNS", CfnOutputProps.builder()
            .value(network.getLoadBalancer().getLoadBalancerDnsName())
            .description("The DNS name of the application load balancer")
            .build());

        app.synth();
    }
}
