package de.westemeyer.version.service;

import de.westemeyer.version.model.Artifact;
import lombok.Value;

@Value
public class MockVersionService implements ArtifactVersionService {
    /** Simple artifact supplied in constructor. */
    Artifact artifact;
}
