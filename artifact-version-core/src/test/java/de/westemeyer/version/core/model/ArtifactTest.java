package de.westemeyer.version.core.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ArtifactTest {
    @Test
    void construction() {
        Assertions.assertDoesNotThrow(() -> new Artifact("group", "artifact", "1.0.0-SNAPSHOT", 0, "version"));
    }

    @Test
    void getters() {
        Artifact artifact = new Artifact("myGroup", "myArtifact", "1.0", 0, null);
        assertEquals("myGroup", artifact.groupId());
        assertEquals("myArtifact", artifact.artifactId());
        assertEquals("1.0", artifact.version());
        assertEquals(0, artifact.timestamp());
    }

    @Test
    void formatTimestamp() {
        Artifact artifact = new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L,
                "Artifact version service definition");
        assertEquals("2021-09-22 23:23:23.686 +0200",
                artifact.formatTimestamp("yyyy-MM-dd HH:mm:ss.SSS Z", TimeZone.getTimeZone("Europe/Paris")));
    }

    @Test
    void toStringTest() {
        Artifact artifact = new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L,
                "Artifact version service definition", "Artifact description", "https://github.com",
                new BasicArtifact("de.westemeyer.parent", "artifact-version-service-parent", "1.1.0-SNAPSHOT", null));
        assertEquals(
                "Artifact[groupId=de.westemeyer, artifactId=artifact-version-service, version=1.0.0-SNAPSHOT, timestamp=1632345803686, name=Artifact version service definition, description=Artifact description, url=https://github.com, parentArtifact=BasicArtifact[groupId=de.westemeyer.parent, artifactId=artifact-version-service-parent, version=1.1.0-SNAPSHOT, parentArtifact=null]]",
                artifact.toString());
    }

    @Test
    void equality() {
        Artifact artifact = new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L,
                "Artifact version service definition");
        assertNotEquals(new Object(), artifact);
        assertNotEquals(null, artifact);
        assertEquals(new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L,
                "Artifact version service definition"), artifact);
        assertNotEquals(new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 123L,
                "Artifact version service definition"), artifact);
        assertNotEquals(new Artifact("abc", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L,
                "Artifact version service definition"), artifact);
        assertNotEquals(new Artifact("de.westemeyer", "abc", "1.0.0-SNAPSHOT", 1632345803686L,
                "Artifact version service definition"), artifact);
        assertNotEquals(new Artifact("de.westemeyer", "artifact-version-service", "abc", 1632345803686L,
                "Artifact version service definition"), artifact);
        assertNotEquals(
                new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "abc"),
                artifact);
        assertNotEquals(
                new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, null),
                artifact);


        assertNotEquals(new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 123L,
                "Artifact version service definition"), artifact);
        assertNotEquals(new Artifact("abc", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L,
                "Artifact version service definition"), artifact);
        assertNotEquals(new Artifact("de.westemeyer", "abc", "1.0.0-SNAPSHOT", 1632345803686L,
                "Artifact version service definition"), artifact);
        assertNotEquals(new Artifact("de.westemeyer", "artifact-version-service", "abc", 1632345803686L,
                "Artifact version service definition"), artifact);
        assertNotEquals(
                new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "abc"),
                artifact);
        assertNotEquals(
                new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, null),
                artifact);

        assertEquals(new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, null),
                new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, null));
    }

    @Test
    void hashCodeTest() {
        Artifact artifact = new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L,
                "Artifact version service definition");
        assertEquals(1821280394, artifact.hashCode());
        artifact = new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 123, null);
        assertEquals(1914264831, artifact.hashCode());
    }
}
