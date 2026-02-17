package de.westemeyer.version.service.test;

import de.westemeyer.version.core.model.Artifact;
import de.westemeyer.version.service.ArtifactVersionCollector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class ArtifactConsumerTest {
    @Test
    void iterateVersions() {
        Set<Artifact> result = new HashSet<>();
        new ArtifactVersionCollector().iterateArtifacts(a -> {
            result.add(a);
            return false;
        });
        Assertions.assertEquals(2, result.size());
    }
}
