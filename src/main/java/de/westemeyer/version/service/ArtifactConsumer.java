package de.westemeyer.version.service;

import de.westemeyer.version.model.Artifact;

import java.util.Set;

/**
 * An artifact consumer is a lambda (or object) which can be used when iterating list of artifacts to search for a
 * specific artifact.
 */
@FunctionalInterface
public interface ArtifactConsumer {
    /**
     * Inspect the artifact content and use it (e.g. to put it in a {@link Set <Artifact>}) or return true when an artifact
     * has been identified to get it returned immediately.
     *
     * @param artifact the artifact from service artifacts to be inspected by handler
     * @return {@code true} to abort search and get the artifact, or {@code false} to keep going
     */
    boolean handleArtifact(Artifact artifact);
}
