package de.westemeyer.version.service;

import de.westemeyer.version.model.Artifact;

/**
 * Service definition is used to supply artifact coordinates with version number.
 * Implementing sub-classes are instantiated using service loader mechanisms.s
 */
public interface ArtifactVersionService {
    /**
     * Get coordinates and version for an artifact (e.g. a jar file).
     * @return method returns artifact coordinates with version
     */
    Artifact getArtifact();
}
