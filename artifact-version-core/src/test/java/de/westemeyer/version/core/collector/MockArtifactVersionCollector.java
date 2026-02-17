package de.westemeyer.version.core.collector;

import de.westemeyer.version.core.api.ArtifactVersionService;
import de.westemeyer.version.core.model.Artifact;

import java.util.ArrayList;
import java.util.List;

public class MockArtifactVersionCollector extends AbstractArtifactVersionCollector {
    /** The group to use in {@link ArtifactVersionService#getArtifact  method. */
    static final String GROUP_ID = "de.loadedGroup";

    /** The artifact to use in {@link ArtifactVersionService#getArtifact} method. */
    static final String ARTIFACT_ID = "loadedArtifact";

    /** The version to use in {@link ArtifactVersionService#getArtifact} method. */
    static final String VERSION = "loadedVersion";

    @Override
    protected Iterable<ArtifactVersionService> getArtifactVersionServices() {
        List<ArtifactVersionService> services = new ArrayList<>();
        services.add(() -> new Artifact(GROUP_ID, ARTIFACT_ID, VERSION, 0, null));
        services.add(() -> new Artifact("de.westemeyer", "artifact-version-core", "1.0.0", 0, "Artifact version service definition"));
        return services;
    }
}
