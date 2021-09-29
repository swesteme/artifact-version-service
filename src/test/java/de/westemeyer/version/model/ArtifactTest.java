package de.westemeyer.version.model;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.TimeZone;

class ArtifactTest {
    @Test
    void construction() {
        Assertions.assertDoesNotThrow(() -> new Artifact("group", "artifact", "1.0.0-SNAPSHOT", 0, "version"));
    }

    @Test
    void getters() {
        Artifact artifact = new Artifact("myGroup", "myArtifact", "1.0", 0, null);
        Assertions.assertEquals("myGroup", artifact.getGroupId());
        Assertions.assertEquals("myArtifact", artifact.getArtifactId());
        Assertions.assertEquals("1.0", artifact.getVersion());
        Assertions.assertEquals(0, artifact.getTimestamp());
    }

    @Test
    void formatTimestamp() {
        Artifact artifact = new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition");
        Assertions.assertEquals("2021-09-22 23:23:23.686 +0200", artifact.formatTimestamp("yyyy-MM-dd HH:mm:ss.SSS Z", TimeZone.getTimeZone("Europe/Paris")));
    }

    @Test
    void toStringTest() {
        Artifact artifact = new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition");
        Assertions.assertEquals("Artifact(groupId=de.westemeyer, artifactId=artifact-version-service, version=1.0.0-SNAPSHOT, timestamp=1632345803686, name=Artifact version service definition)", artifact.toString());
    }

    @Test
    void equality() {
        Artifact artifact = new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition");
        Assertions.assertEquals(artifact, artifact);
        Assertions.assertNotEquals(artifact, new Object());
        Assertions.assertNotEquals(null, artifact);
        Assertions.assertEquals(artifact, new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition"));
        Assertions.assertNotEquals(artifact, new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 123L, "Artifact version service definition"));
        Assertions.assertNotEquals(artifact, new Artifact("abc", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition"));
        Assertions.assertNotEquals(artifact, new Artifact("de.westemeyer", "abc", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition"));
        Assertions.assertNotEquals(artifact, new Artifact("de.westemeyer", "artifact-version-service", "abc", 1632345803686L, "Artifact version service definition"));
        Assertions.assertNotEquals(artifact, new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "abc"));
        Assertions.assertNotEquals(artifact, new Artifact(null, "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition"));
        Assertions.assertNotEquals(artifact, new Artifact("de.westemeyer", null, "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition"));
        Assertions.assertNotEquals(artifact, new Artifact("de.westemeyer", "artifact-version-service", null, 1632345803686L, "Artifact version service definition"));
        Assertions.assertNotEquals(artifact, new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, null));


        Assertions.assertNotEquals(new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 123L, "Artifact version service definition"), artifact);
        Assertions.assertNotEquals(new Artifact("abc", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition"), artifact);
        Assertions.assertNotEquals(new Artifact("de.westemeyer", "abc", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition"), artifact);
        Assertions.assertNotEquals(new Artifact("de.westemeyer", "artifact-version-service", "abc", 1632345803686L, "Artifact version service definition"), artifact);
        Assertions.assertNotEquals(new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "abc"), artifact);
        Assertions.assertNotEquals(new Artifact(null, "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition"), artifact);
        Assertions.assertNotEquals(new Artifact("de.westemeyer", null, "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition"), artifact);
        Assertions.assertNotEquals(new Artifact("de.westemeyer", "artifact-version-service", null, 1632345803686L, "Artifact version service definition"), artifact);
        Assertions.assertNotEquals(new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, null), artifact);
    }

    @Test
    void hashCodeTest() {
        Artifact artifact = new Artifact("de.westemeyer", "artifact-version-service", "1.0.0-SNAPSHOT", 1632345803686L, "Artifact version service definition");
        Assertions.assertEquals(-987012903, artifact.hashCode());
        artifact = new Artifact(null, null, null, 123, null);
        Assertions.assertEquals(-2080624034, artifact.hashCode());
    }
}
