package de.westemeyer.version.service;

import de.westemeyer.version.model.Artifact;
import de.westemeyer.version.model.DefaultArtifactComparator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

class ArtifactVersionCollectorTest {
    @Test
    void collectArtifacts() {
        Set<Artifact> artifacts = ArtifactVersionCollector.collectArtifacts();
        List<Artifact> list = new ArrayList<>(artifacts);
        Assertions.assertEquals(2, list.size());
        Artifact artifact = list.get(0);
        Assertions.assertEquals("de.loadedGroup", artifact.getGroupId());
        Assertions.assertEquals("loadedArtifact", artifact.getArtifactId());
        Assertions.assertEquals("loadedVersion", artifact.getVersion());
        Assertions.assertNull(artifact.getName());

        artifacts = new ArtifactVersionCollector(Comparator.comparing(Artifact::getVersion)).collect();
        list = new ArrayList<>(artifacts);
        Assertions.assertEquals(2, list.size());
        artifact = list.get(0);
        Assertions.assertEquals("de.westemeyer", artifact.getGroupId());
        Assertions.assertEquals("artifact-version-service", artifact.getArtifactId());
        Assertions.assertEquals("1.0.0", artifact.getVersion());
        Assertions.assertEquals("Artifact version service definition", artifact.getName());
    }

    @Test
    void findArtifact() {
        Assertions.assertNotNull(ArtifactVersionCollector.findArtifact("de.westemeyer", "artifact-version-service"));
        Assertions.assertNull(ArtifactVersionCollector.findArtifact("de.westemeyer", "abc"));
        Assertions.assertNull(ArtifactVersionCollector.findArtifact("de.westemeyer", null));
        Assertions.assertNull(ArtifactVersionCollector.findArtifact(null, "artifact-version-service"));
    }

    @Test
    void findArtifactsByGroupId() {
        Assertions.assertEquals(Collections.emptySet(), ArtifactVersionCollector.findArtifactsByGroupId("de.westemeyer.oops", true));
        Assertions.assertEquals(Collections.emptySet(), ArtifactVersionCollector.findArtifactsByGroupId("de.westemeyer.oops", false));
        Assertions.assertEquals(2, ArtifactVersionCollector.findArtifactsByGroupId("de.", false).size());
        Assertions.assertEquals(2, ArtifactVersionCollector.findArtifactsByGroupId(null, false).size());
        Assertions.assertEquals(0, ArtifactVersionCollector.findArtifactsByGroupId(null, true).size());
        Assertions.assertEquals(1, ArtifactVersionCollector.findArtifactsByGroupId("de.westemeyer", false).size());
        Assertions.assertEquals(1, ArtifactVersionCollector.findArtifactsByGroupId("de.westemeyer", true).size());

        Assertions.assertEquals(2, new ArtifactVersionCollector(Comparator.comparing(Artifact::getVersion)).artifactsByGroupId("de.", false).size());
    }

    @Test
    void printVersions() {
        ArtifactVersionCollector.iterateArtifacts(a -> {
            System.out.println(a);
            return false;
        });
    }
}
