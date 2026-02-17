package de.westemeyer.version.core.collector;

import de.westemeyer.version.core.api.ArtifactConsumer;
import de.westemeyer.version.core.api.ArtifactVersionService;
import de.westemeyer.version.core.model.Artifact;
import de.westemeyer.version.core.model.DefaultArtifactComparator;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


/**
 * Collector class is used to aggregate all artifact POJOs into a Set of artifacts.
 */
public abstract class AbstractArtifactVersionCollector {

    /**
     * Collect all artifact coordinates and their versions using service loader.
     *
     * @return sorted set of artifacts
     */
    public Set<Artifact> collect() {
        // return sorted set of artifacts
        return collect(new DefaultArtifactComparator());
    }

    /**
     * Collect all artifact coordinates and their versions using service loader.
     *
     * @param sortingComparator comparator used for order in return value
     * @return sorted set of artifacts
     */
    public Set<Artifact> collect(Comparator<Artifact> sortingComparator) {
        // create a new sorted result set
        Set<Artifact> artifacts = new TreeSet<>(sortingComparator);

        // add all artifact objects to set of artifacts
        iterateArtifacts(artifact -> {
            // add artifact
            artifacts.add(artifact);

            // keep adding artifacts
            return false;
        });

        // return sorted set of artifacts
        return artifacts;
    }

    /**
     * Find a given artifact by its coordinates (just without the version).
     *
     * @param groupId    the group id
     * @param artifactId the artifact id
     * @return the artifact identified by its coordinates or {@code null} in case it is not found
     */
    public Artifact artifactsByGroupIdAndArtifactId(String groupId, String artifactId) {
        return iterateArtifacts(artifact ->
                groupId != null && groupId.equals(artifact.groupId()) && artifactId != null && artifactId.equals(artifact.artifactId())
        );
    }

    /**
     * Find a set of artifacts by groupId or groupId prefix. Use the instance sorting comparator to create Set.
     *
     * @param groupId the groupId or groupId prefix
     * @param exact   {@code true} to check for exact groupId, {@code false} to use {@code groupId} as a prefix
     * @return a set of artifacts matching the criteria
     */
    public Set<Artifact> artifactsByGroupId(String groupId, boolean exact) {
        return artifactsByGroupId(groupId, exact, new DefaultArtifactComparator());
    }

    /**
     * Find a set of artifacts by groupId or groupId prefix. Use the instance sorting comparator to create Set.
     *
     * @param groupId           the groupId or groupId prefix
     * @param exact             {@code true} to check for exact groupId, {@code false} to use {@code groupId} as a prefix
     * @param sortingComparator comparator used for order in return value
     * @return a set of artifacts matching the criteria
     */
    public Set<Artifact> artifactsByGroupId(String groupId, boolean exact, Comparator<Artifact> sortingComparator) {
        // create a new sorted result set
        Set<Artifact> artifacts = new TreeSet<>(sortingComparator);

        // add all artifact objects to set of artifacts
        iterateArtifacts(artifact -> {
            // get group id from artifact
            String artifactGroupId = artifact.groupId();

            // check condition to add artifact to set
            if (groupId == null && !exact || exact && artifactGroupId.equals(groupId) || !exact && artifactGroupId.startsWith(groupId)) {
                // add artifact
                artifacts.add(artifact);
            }

            // keep adding artifacts
            return false;
        });

        // return sorted set of artifacts
        return artifacts;
    }

    /**
     * Iterate list of artifacts and check them using a given lambda. See {@link #artifactsByGroupIdAndArtifactId(String, String)} and
     * {@link #artifactsByGroupId(String, boolean)} for examples.
     *
     * @param consumer the artifact consumer (see {@link ArtifactConsumer} for details
     * @return an artifact or {@code null}
     */
    public Artifact iterateArtifacts(ArtifactConsumer consumer) {
        // iterate all published service classes
        for (ArtifactVersionService versionService : getArtifactVersionServices()) {
            // get artifact from service
            Artifact artifact = versionService.getArtifact();

            // handle artifact using consumer instance
            if (consumer.handleArtifact(artifact)) {
                // return only the artifact in case consumer returned true
                return artifact;
            }
        }

        // default: return null
        return null;
    }

    /**
     * Used in subclasses to provide an {@link Iterable} of {@link ArtifactVersionService} objects.
     *
     * @return an {@link Iterable} of {@link ArtifactVersionService} objects
     */
    protected abstract Iterable<ArtifactVersionService> getArtifactVersionServices();
}
