package de.westemeyer.version.core.collector;

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

class AbstractArtifactVersionCollectorTest {

    @Test
    void collect() {
        Set<Artifact> artifacts = new MockArtifactVersionCollector().collect();
        List<Artifact> list = new ArrayList<>(artifacts);
        assertEquals(2, list.size());
        Artifact artifact = list.get(0);
        assertEquals("de.loadedGroup", artifact.groupId());
        assertEquals("loadedArtifact", artifact.artifactId());
        assertEquals("loadedVersion", artifact.version());
        assertNull(artifact.name());

        artifacts = new MockArtifactVersionCollector().collect(Comparator.comparing(Artifact::version));
        list = new ArrayList<>(artifacts);
        assertEquals(2, list.size());
        artifact = list.get(0);
        assertEquals("de.westemeyer", artifact.groupId());
        assertEquals("artifact-version-core", artifact.artifactId());
        assertEquals("Artifact version service definition", artifact.name());
    }

    @Test
    void artifactsByGroupIdAndArtifactId() {
        MockArtifactVersionCollector collector = new MockArtifactVersionCollector();
        assertNotNull(collector.artifactsByGroupIdAndArtifactId("de.westemeyer", "artifact-version-core"));
        assertNull(collector.artifactsByGroupIdAndArtifactId("de.westemeyer", "abc"));
        assertNull(collector.artifactsByGroupIdAndArtifactId("de.westemeyer", null));
        assertNull(collector.artifactsByGroupIdAndArtifactId(null, "artifact-version-core"));
    }

    @Test
    void artifactsByGroupId() {
        MockArtifactVersionCollector collector = new MockArtifactVersionCollector();
        assertEquals(Collections.emptySet(), collector.artifactsByGroupId("de.westemeyer.oops", true));
        assertEquals(Collections.emptySet(), collector.artifactsByGroupId("de.westemeyer.oops", false));
        assertEquals(2, collector.artifactsByGroupId("de.", false).size());
        assertEquals(2, collector.artifactsByGroupId(null, false).size());
        assertEquals(0, collector.artifactsByGroupId(null, true).size());
        assertEquals(1, collector.artifactsByGroupId("de.westemeyer", false).size());
        assertEquals(1, collector.artifactsByGroupId("de.westemeyer", true).size());

        assertEquals(2, new MockArtifactVersionCollector().artifactsByGroupId("de.", false, Comparator.comparing(Artifact::version)).size());
    }

    @Test
    void printVersions() {
        new MockArtifactVersionCollector().iterateArtifacts(a -> {
            System.out.println(a);
            return false;
        });
    }
}
