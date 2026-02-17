# Artifact version service

![Build Status Linux](https://github.com/swesteme/artifact-version-service/actions/workflows/ci.yml/badge.svg)
[![GitHub release](https://img.shields.io/github/release/swesteme/artifact-version-service.svg?label=changelog)](https://github.com/swesteme/artifact-version-service/releases/latest)
[![codecov](https://codecov.io/gh/swesteme/artifact-version-service/branch/main/graph/badge.svg?token=QTB0PSUS2X)](https://codecov.io/gh/swesteme/artifact-version-service)

The `artifact-version-service` or the `artifact-version-service-spring-boot` are used to find artifact versions on the classpath. 
It uses Javas own classloader mechanism or Spring Boot services. All "participating" jar files can contribute their own version by supplying 
their own implementation of the `ArtifactVersionService`.

This is made particularly easy by the accompanying maven plugin `artifact-version-maven-plugin`. Simply add this generator plugin to the maven build
of your Java modules (or add it to your companies parent pom) and the service is generated automatically, keeping the artifact version up to date.

artifact-version-service is published under the
[MIT license](http://opensource.org/licenses/MIT). It requires at least Java 17.

### Fetching all modules with coordinates
No more reading jar manifests, just a simple method call:

```java
private void printArtifacts() {
    // iterate list of artifact dependencies
    for (Artifact artifact : ArtifactVersionCollector.collectArtifacts()) {
        // print simple artifact string example
        System.out.println("artifact = " + artifact);
    }
}
```
Or using Spring service injection:

```java
@Autowired
private ArtifactVersionCollector collector;

private void printArtifacts() {
    // iterate list of artifact dependencies
    for (Artifact artifact : collector.collectArtifacts()) {
        // print simple artifact string example
        System.out.println("artifact = " + artifact);
    }
}
```

A sorted set of artifacts is returned. To modify the sorting order, provide a custom comparator:
```java
collector.collect(Comparator.comparing(Artifact::version));
```
This way the list of artifacts is returned sorted by version numbers. 

### Find a specific artifact
```java
collector.artifactsByGroupIdAndArtifactId("de.westemeyer", "artifact-version-service-spring-boot");
```
Fetches the version details for a specific artifact.

### Find artifacts with matching groupId(s)
Find all artifacts with groupId `de.westemeyer` (exact match):
```java
collector.artifactsByGroupId("de.westemeyer", true);
```

Find all artifacts where groupId *starts with* `de.westemeyer`:
```java
collector.artifactsByGroupId("de.westemeyer", false);
```

Sort result by version number:
```java
collector.artifactsByGroupId("de.", false, Comparator.comparing(Artifact::version));
```

### Implement custom actions on list of artifacts
By supplying a lambda, the very first example could be implemented like this:
```java
collector.iterateArtifacts(artifact -> {
        System.out.println("artifact = " + artifact);
    return false;
});
```

## Installation

artifact-version-maven-service is available from
[Maven Central](https://search.maven.org/artifact/de.westemeyer/artifact-version-service).

It is used in combination with the [artifact-version-maven-plugin](https://github.com/swesteme/artifact-version-maven-plugin) maven source code generator. Make sure to read the documentation for all configuration options.
```xml
<build>
  <plugins>
    <plugin>
      <groupId>de.westemeyer</groupId>
      <artifactId>artifact-version-maven-plugin</artifactId>
      <version>2.0.0</version>
      <executions>
        <execution>
          <goals>
            <goal>generate-service</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
    <plugin>
      <!-- Add source folder to Eclipse configuration. IntelliJ will recognize extra source automatically. -->
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>build-helper-maven-plugin</artifactId>
      <version>3.0.0</version>
      <executions>
          <execution>
              <id>add-source</id>
              <phase>generate-sources</phase>
              <goals>
                  <goal>add-source</goal>
              </goals>
              <configuration>
                  <sources>
                      <source>${project.build.directory}/generated-sources/artifact-versions</source>
                  </sources>
              </configuration>
          </execution>
      </executions>
    </plugin>
  </plugins>
</build>

<dependencies>
  <dependency>
    <groupId>de.westemeyer</groupId>
    <artifactId>artifact-version-service-spring-boot</artifactId>
    <version>2.0.0</version>
  </dependency>
</dependencies>
```

It is also possible to configure the generator to use target directories and a more specific service class definition:
```xml
<build>
  <plugins>
    <plugin>
      <groupId>de.westemeyer</groupId>
      <artifactId>artifact-version-maven-plugin</artifactId>
      <version>2.0.0</version>
      <executions>
        <execution>
          <goals>
            <goal>generate-service</goal>
          </goals>
        </execution>
      </executions>
      <configuration>
        <!-- for Spring services, if skipSpringBootAutoConfiguration is set: make sure to generate into a base package or below -->
        <packageName>my.generated.service</packageName>
        <serviceClass>MyGeneratedServiceClass</serviceClass>
        <targetFolder>target/generated-sources</targetFolder>
        <!-- or use NATIVE for plain Java services, SPRING_BOOT is the default value -->
        <serviceType>SPRING_BOOT</serviceType>
      </configuration>
    </plugin>
    <plugin>
      <!-- Add source folder to Eclipse configuration. IntelliJ will recognize extra source automatically. -->
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>build-helper-maven-plugin</artifactId>
      <version>3.0.0</version>
      <executions>
          <execution>
              <id>add-source</id>
              <phase>generate-sources</phase>
              <goals>
                  <goal>add-source</goal>
              </goals>
              <configuration>
                  <sources>
                      <source>target/generated-sources</source>
                  </sources>
              </configuration>
          </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

## Contributing

You have three options if you have a feature request, found a bug or
simply have a question about artifact-version-service:

* [Write an issue.](https://github.com/swesteme/artifact-version-service/issues/new)
* Create a pull request. (See [Understanding the GitHub Flow](https://guides.github.com/introduction/flow/index.html))
* [Write an eMail to sebastian@westemeyer.de](mailto:sebastian@westemeyer.de)

## Development Guide

artifact-version-service is built with [Maven](http://maven.apache.org/) and must be
compiled using JDK 17. If you want to contribute code then

* Please write a test for your change.
* Ensure that you didn't break the build by running `mvn clean verify -Dgpg.skip`.
* Fork the repo and create a pull request. (See [Understanding the GitHub Flow](https://guides.github.com/introduction/flow/index.html))
