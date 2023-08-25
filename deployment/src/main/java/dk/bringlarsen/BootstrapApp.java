package dk.bringlarsen;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

/**
 * This is an empty app that we can use for bootstrapping the CDK with the "cdk bootstrap" command.
 * We could do this with other apps, but this would require us to enter all the parameters
 * for that app, which is uncool.
 */
public class BootstrapApp {

    public static void main(final String[] args) {
        App app = new App();
        Configuration configuration = new Configuration();
        Environment awsEnvironment = Environment.builder()
            .account(configuration.getAccountId())
            .region(configuration.getRegion())
            .build();
        new Stack(app, "Bootstrap", StackProps.builder().env(awsEnvironment).build());
        app.synth();
    }
}
