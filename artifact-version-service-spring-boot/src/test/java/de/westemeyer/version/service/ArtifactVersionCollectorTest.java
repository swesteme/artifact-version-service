package de.westemeyer.version.service;

import de.westemeyer.version.core.model.Artifact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = TestConfiguration.class)
class ArtifactVersionCollectorTest {

    @Autowired
    private ArtifactVersionCollector artifactVersionCollector;

    @Test
    void collectArtifacts() {
        Set<Artifact> artifacts = artifactVersionCollector.collect();
        List<Artifact> list = new ArrayList<>(artifacts);
        assertEquals(2, list.size());
        Artifact artifact = list.get(0);
        assertEquals("de.loadedGroup", artifact.groupId());
        assertEquals("loadedArtifact", artifact.artifactId());
        assertEquals("loadedVersion", artifact.version());
        assertNull(artifact.name());

        artifacts = artifactVersionCollector.collect(Comparator.comparing(Artifact::version));
        list = new ArrayList<>(artifacts);
        assertEquals(2, list.size());
        artifact = list.get(0);
        assertEquals("de.westemeyer", artifact.groupId());
        assertEquals("artifact-version-service-spring-boot", artifact.artifactId());
        assertEquals("Artifact version service for Spring Boot", artifact.name());
    }

    @Test
    void findArtifact() {
        assertNotNull(artifactVersionCollector.artifactsByGroupIdAndArtifactId("de.westemeyer",
                "artifact-version-service-spring-boot"));
        assertNull(artifactVersionCollector.artifactsByGroupIdAndArtifactId("de.westemeyer", "abc"));
        assertNull(artifactVersionCollector.artifactsByGroupIdAndArtifactId("de.westemeyer", null));
        assertNull(
                artifactVersionCollector.artifactsByGroupIdAndArtifactId(null, "artifact-version-service-spring-boot"));
    }

    @Test
    void findArtifactsByGroupId() {
        assertEquals(Collections.emptySet(), artifactVersionCollector.artifactsByGroupId("de.westemeyer.oops", true));
        assertEquals(Collections.emptySet(), artifactVersionCollector.artifactsByGroupId("de.westemeyer.oops", false));
        assertEquals(2, artifactVersionCollector.artifactsByGroupId("de.", false).size());
        assertEquals(2, artifactVersionCollector.artifactsByGroupId(null, false).size());
        assertEquals(0, artifactVersionCollector.artifactsByGroupId(null, true).size());
        assertEquals(1, artifactVersionCollector.artifactsByGroupId("de.westemeyer", false).size());
        assertEquals(1, artifactVersionCollector.artifactsByGroupId("de.westemeyer", true).size());

        assertEquals(2,
                artifactVersionCollector.artifactsByGroupId("de.", false, Comparator.comparing(Artifact::version))
                        .size());
    }

    @Test
    void printVersions() {
        artifactVersionCollector.iterateArtifacts(artifact -> {
            System.out.println("artifact = " + artifact);
            return false;
        });
    }
}
