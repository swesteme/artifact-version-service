package de.westemeyer.version.service;

import de.westemeyer.version.model.Artifact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArtifactVersionServiceTest {
    @Test
    void getVersion() {
        Artifact myArtifact = new Artifact("group", "artifact", "version", 0, "name");
        ArtifactVersionService service = new MockVersionService(myArtifact);
        Assertions.assertEquals(myArtifact, service.getArtifact());
    }
}
