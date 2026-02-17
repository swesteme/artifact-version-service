package de.westemeyer.version.core.model;

import lombok.NonNull;

/**
 * Simple interface used for artifact coordinates.
 *
 */
public interface ArtifactCoordinates {
    /**
     * Get the groupId property of the artifact.
     *
     * @return the groupId property of the artifact.
     */
    @NonNull
    String groupId();

    /**
     * Get the artifactId property of the artifact.
     *
     * @return the artifactId property of the artifact.
     */
    @NonNull
    String artifactId();

    /**
     * Get the version property of the artifact.
     *
     * @return the version property of the artifact.
     */
    @NonNull
    String version();

    /**
     * Get the (optional) parent artifact.
     *
     * @return the (optional) parent artifact.
     */
    ArtifactCoordinates parentArtifact();
}
