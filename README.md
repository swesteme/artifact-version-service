# Artifact version service maven generator plugin

![Build Status Linux](https://github.com/swesteme/artifact-version-service/workflows/Java%20CI%20with%20Maven/badge.svg?branch=main) [![codecov](https://codecov.io/gh/swesteme/artifact-version-service/branch/main/graph/badge.svg?token=QTB0PSUS2X)](https://codecov.io/gh/swesteme/artifact-version-service)

The artifact-version-service is used to find artifact versions on the classpath. It uses Javas own classloader mechanism. All "participating" jar files can contribute their own version by supplying their own implementation of the `ArtifactVersionService`.

This is made particularly easy by the accompanying maven plugin `artifact-version-maven-plugin`. Simply add this generator plugin to the maven build of your Java modules (or add it to your companies parent pom) and the service is generated automatically, keeping the artifact version up to date.

artifact-version-service is published under the
[MIT license](http://opensource.org/licenses/MIT). It requires at least Java 8.

### Fetching all modules with coordinates
No more reading jar manifests, just a simple method call:

```java
// iterate list of artifact dependencies
for (Artifact artifact : ArtifactVersionCollector.collectArtifacts()) {
    // print simple artifact string example
    System.out.println("artifact = " + artifact);
}
```
A sorted set of artifacts is returned. To modify the sorting order, provide a custom comparator:
```java
new ArtifactVersionCollector(Comparator.comparing(Artifact::getVersion)).collect();
```
This way the list of artifacts is returned sorted by version numbers. 

### Find a specific artifact
```java
ArtifactVersionCollector.findArtifact("de.westemeyer", "artifact-version-service");
```
Fetches the version details for a specific artifact.

### Find artifacts with matching groupId(s)
Find all artifacts with groupId `de.westemeyer` (exact match):
```java
ArtifactVersionCollector.findArtifactsByGroupId("de.westemeyer", true);
```

Find all artifacts where groupId *starts with* `de.westemeyer`:
```java
ArtifactVersionCollector.findArtifactsByGroupId("de.westemeyer", false);
```

Sort result by version number:
```java
new ArtifactVersionCollector(Comparator.comparing(Artifact::getVersion)).artifactsByGroupId("de.", false);
```

### Implement custom actions on list of artifacts
By supplying a lambda, the very first example could be implemented like this:
```java
ArtifactVersionCollector.iterateArtifacts(a -> {
    System.out.println(a);
    return false;
});
```

## Installation

artifact-version-maven-service is available from
[Maven Central](https://search.maven.org/artifact/de.westemeyer/artifact-version-service).

It is used in combination with the [artifact-version-maven-plugin](https://github.com/swesteme/artifact-version-maven-plugin) maven source code generator.
```xml
<build>
  <plugins>
    <plugin>
      <groupId>de.westemeyer</groupId>
      <artifactId>artifact-version-maven-plugin</artifactId>
      <version>1.0.0</version>
      <executions>
        <execution>
          <goals>
            <goal>generate-service</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>

<dependencies>
  <dependency>
    <groupId>de.westemeyer</groupId>
    <artifactId>artifact-version-service</artifactId>
    <version>1.0.0</version>
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
      <version>1.0.0</version>
      <executions>
        <execution>
          <goals>
            <goal>generate-service</goal>
          </goals>
        </execution>
      </executions>
      <configuration>
        <packageName>my.package.for.generated.service.class</packageName>
        <serviceClass>MyGeneratedServiceClass</serviceClass>
        <targetFolder>target/generated-sources</targetFolder>
      </configuration>
    </plugin>
  </plugins>
</build>
```

## Display generated Code in your IDE
IntelliJ IDEA should show generated Java source files as soon as "Packages" perspective is selected in "Project" view. 

## Contributing

You have three options if you have a feature request, found a bug or
simply have a question about artifact-version-service:

* [Write an issue.](https://github.com/swesteme/artifact-version-service/issues/new)
* Create a pull request. (See [Understanding the GitHub Flow](https://guides.github.com/introduction/flow/index.html))
* [Write an eMail to sebastian@westemeyer.de](mailto:sebastian@westemeyer.de)

## Development Guide

artifact-version-service is built with [Maven](http://maven.apache.org/) and must be
compiled using JDK 8. If you want to contribute code then

* Please write a test for your change.
* Ensure that you didn't break the build by running `mvn clean verify -Dgpg.skip`.
* Fork the repo and create a pull request. (See [Understanding the GitHub Flow](https://guides.github.com/introduction/flow/index.html))
