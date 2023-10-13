# Spring Boot Exploration project

## Build

To build the project do:

    mvn package

## Startup

First build the project using `mvn package` to generate some properties required at runtime.
Then just run the main class of the application from your IDE:

    presentation/web-api/src/main/java/dk/bringlarsen/application/Application.java

The Spring Boot Developer Tools are automatically active and the server is automatically restarted
when a class is recompiled in any of the Maven modules, e.g. in Intellij IDEA by executing `Ctrl-Shift-F9` 
on a source file.

Open [OpenApi](http://localhost:8080/swagger-ui.html) or [Actuator](http://localhost:8080/actuator)
to verify that the server is up and running.

To start up the project locally using Maven do:

`mvn spring-boot:run -pl :web-api`

### Configuration

If you wish to override the properties provided on classpath use the `spring.config.additional-location` property. Example when using the Spring boot Maven plugin:

`mvn spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="-Dspring.config.additional-location=optional:file:c:/temp/app.properties" -pl :web-api`

See the [Spring documentation](https://docs.spring.io/spring-boot/docs/3.1.4/reference/htmlsingle/#features.external-config.files) for details.

### Profiles

There are three profiles `dev`, `test` and `prod` defined in the `application.properties` file. 
By default, the `prod` profile is active and to use another profile add the VM option:

    -Dspring.profiles.active=dev

Doing the same using the Spring Boot Maven plugin: 
`mvn spring-boot:run -Dspring-boot.run.profiles=dev -pl :web-api`

## Packaging

By default, the application is packaged as a runnable jar. To wrap the application in a container see
[this](https://spring.io/guides/topicals/spring-boot-docker) guide and a good start is to use the provided
[Google Jib plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin): 
`mvn jib:dockerBuild -pl :web-api`

Inspect the image using `docker images` and to start the container use the command: `docker run -p 8080:8080 dk.bringlarsen/springboot-exploration`

Push it do Docker Hub by doing:

    docker login
    docker push jebla/springboot-exploration:latest

## Development

During development a database is needed and one can be spun up with the command:
 
    cd presentation/web-api/src/docker
    docker compose start db

Happy coding!

