package de.westemeyer.version.service;

import de.westemeyer.version.core.model.Artifact;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ArtifactVersionCollectorTest {
    @Test
    void collectArtifacts() {
        Set<Artifact> artifacts = ArtifactVersionCollector.collectArtifacts();
        List<Artifact> list = new ArrayList<>(artifacts);
        assertEquals(2, list.size());
        Artifact artifact = list.get(0);
        assertEquals("de.loadedGroup", artifact.groupId());
        assertEquals("loadedArtifact", artifact.artifactId());
        assertEquals("loadedVersion", artifact.version());
        assertNull(artifact.name());

        artifacts = new ArtifactVersionCollector().collect(Comparator.comparing(Artifact::version));
        list = new ArrayList<>(artifacts);
        assertEquals(2, list.size());
        artifact = list.get(0);
        assertEquals("de.westemeyer", artifact.groupId());
        assertEquals("artifact-version-service", artifact.artifactId());
        assertEquals("Artifact version service plain Java definition", artifact.name());
    }

    @Test
    void findArtifact() {
        assertNotNull(ArtifactVersionCollector.findArtifact("de.westemeyer", "artifact-version-service"));
        assertNull(ArtifactVersionCollector.findArtifact("de.westemeyer", "abc"));
        assertNull(ArtifactVersionCollector.findArtifact("de.westemeyer", null));
        assertNull(ArtifactVersionCollector.findArtifact(null, "artifact-version-service"));
    }

    @Test
    void findArtifactsByGroupId() {
        assertEquals(Collections.emptySet(), ArtifactVersionCollector.findArtifactsByGroupId("de.westemeyer.oops", true));
        assertEquals(Collections.emptySet(), ArtifactVersionCollector.findArtifactsByGroupId("de.westemeyer.oops", false));
        assertEquals(2, ArtifactVersionCollector.findArtifactsByGroupId("de.", false).size());
        assertEquals(2, ArtifactVersionCollector.findArtifactsByGroupId(null, false).size());
        assertEquals(0, ArtifactVersionCollector.findArtifactsByGroupId(null, true).size());
        assertEquals(1, ArtifactVersionCollector.findArtifactsByGroupId("de.westemeyer", false).size());
        assertEquals(1, ArtifactVersionCollector.findArtifactsByGroupId("de.westemeyer", true).size());

        assertEquals(2, new ArtifactVersionCollector().artifactsByGroupId("de.", false, Comparator.comparing(Artifact::version)).size());
    }

    @Test
    void printVersions() {
        new ArtifactVersionCollector().iterateArtifacts(a -> {
            System.out.println(a);
            return false;
        });
    }
}
