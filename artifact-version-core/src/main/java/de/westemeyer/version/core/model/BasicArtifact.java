package de.westemeyer.version.core.model;

import lombok.NonNull;

/**
 * Simple object used to keep artifact coordinates and version in one place.
 *
 * @param groupId        the groupId property of the artifact.
 * @param artifactId     the artifactId property of the artifact.
 * @param version        the version property of the artifact.
 * @param parentArtifact parent artifact "next level" used to create hierarchy.
 */
public record BasicArtifact(@NonNull String groupId, @NonNull String artifactId, @NonNull String version,
                            ArtifactCoordinates parentArtifact) implements ArtifactCoordinates {

}
