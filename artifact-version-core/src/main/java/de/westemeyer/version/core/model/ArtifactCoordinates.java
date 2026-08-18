package de.westemeyer.version.core.model;

import org.jspecify.annotations.Nullable;

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
    String groupId();

    /**
     * Get the artifactId property of the artifact.
     *
     * @return the artifactId property of the artifact.
     */
    String artifactId();

    /**
     * Get the version property of the artifact.
     *
     * @return the version property of the artifact.
     */
    String version();

    /**
     * Get the (optional) parent artifact.
     *
     * @return the (optional) parent artifact.
     */
    @Nullable
    ArtifactCoordinates parentArtifact();
}
