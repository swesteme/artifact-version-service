package de.westemeyer.version.service;

import de.westemeyer.version.core.api.ArtifactVersionService;
import de.westemeyer.version.core.collector.AbstractArtifactVersionCollector;
import de.westemeyer.version.core.model.Artifact;

import java.util.ServiceLoader;
import java.util.Set;


/**
 * Collector class is used to aggregate all artifact POJOs into a Set of artifacts.
 */
public class ArtifactVersionCollector extends AbstractArtifactVersionCollector {

    /**
     * Collect all artifact coordinates and their versions using service loader.
     *
     * @return sorted set of artifacts
     */
    public static Set<Artifact> collectArtifacts() {
        return new ArtifactVersionCollector().collect();
    }

    /**
     * Find a given artifact by its coordinates (just without the version).
     *
     * @param groupId    the group id
     * @param artifactId the artifact id
     * @return the artifact identified by its coordinates or {@code null} in case it is not found
     */
    public static Artifact findArtifact(String groupId, String artifactId) {
        return new ArtifactVersionCollector().artifactsByGroupIdAndArtifactId(groupId, artifactId);
    }

    /**
     * Find a set of artifacts by groupId or groupId prefix.
     *
     * @param groupId the groupId or groupId prefix
     * @param exact   {@code true} to check for exact groupId, {@code false} to use {@code groupId} as a prefix
     * @return a set of artifacts matching the criteria
     */
    public static Set<Artifact> findArtifactsByGroupId(String groupId, boolean exact) {
        return new ArtifactVersionCollector().artifactsByGroupId(groupId, exact);
    }

    @Override
    protected Iterable<ArtifactVersionService> getArtifactVersionServices() {
        return ServiceLoader.load(ArtifactVersionService.class);
    }
}
