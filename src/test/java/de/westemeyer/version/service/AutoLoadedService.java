package de.westemeyer.version.service;

import com.google.auto.service.AutoService;
import de.westemeyer.version.model.Artifact;

@AutoService(ArtifactVersionService.class)
public class AutoLoadedService implements ArtifactVersionService {
    /** The group to use in {@link #getArtifact  method. */
    static final String GROUP_ID = "de.loadedGroup";

    /** The artifact to use in {@link #getArtifact} method. */
    static final String ARTIFACT_ID = "loadedArtifact";

    /** The version to use in {@link #getArtifact} method. */
    static final String VERSION = "loadedVersion";

    @Override
    public Artifact getArtifact() {
        return new Artifact(GROUP_ID, ARTIFACT_ID, VERSION, 0, null);
    }
}
