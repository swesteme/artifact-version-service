package de.westemeyer.version.service;

import de.westemeyer.version.model.Artifact;
import de.westemeyer.version.model.DefaultArtifactComparator;

import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;


/**
 * Collector class is used to aggregate all artifact POJOs into a Set of artifacts.
 */
public class ArtifactVersionCollector {
    /** The comparator determining the order of artifacts. */
    private final Comparator<Artifact> sortingComparator;

    /**
     * Default constructor using default artifact comparator.
     */
    public ArtifactVersionCollector() {
        this(new DefaultArtifactComparator());
    }

    /**
     * Create a new collector using a custom sorting comparator.
     * @param sortingComparator the comparator to determine the order of artifacts
     */
    public ArtifactVersionCollector(Comparator<Artifact> sortingComparator) {
        this.sortingComparator = sortingComparator;
    }

    /**
     * Collect all artifact coordinates and their versions using service loader.
     *
     * @return sorted set of artifacts
     */
    public static Set<Artifact> collectArtifacts() {
        return new ArtifactVersionCollector().collect();
    }

    /**
     * Collect all artifact coordinates and their versions using service loader.
     *
     * @return sorted set of artifacts
     */
    public Set<Artifact> collect() {
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
    public static Artifact findArtifact(String groupId, String artifactId) {
        return iterateArtifacts(artifact ->
                groupId != null && groupId.equals(artifact.getGroupId()) && artifactId != null && artifactId.equals(artifact.getArtifactId())
        );
    }

    /**
     * Find a set of artifacts by groupId or groupId prefix.
     *
     * @param groupId the groupId or groupId prefix
     * @param exact {@code true} to check for exact groupId, {@code false} to use {@code groupId} as a prefix
     * @return a set of artifacts matching the criteria
     */
    public static Set<Artifact> findArtifactsByGroupId(String groupId, boolean exact) {
        return new ArtifactVersionCollector().artifactsByGroupId(groupId, exact);
    }

    /**
     * Find a set of artifacts by groupId or groupId prefix. Use the instance sorting comparator to create Set.
     *
     * @param groupId the groupId or groupId prefix
     * @param exact {@code true} to check for exact groupId, {@code false} to use {@code groupId} as a prefix
     * @return a set of artifacts matching the criteria
     */
    public Set<Artifact> artifactsByGroupId(String groupId, boolean exact) {
        // create a new sorted result set
        Set<Artifact> artifacts = new TreeSet<>(sortingComparator);

        // add all artifact objects to set of artifacts
        iterateArtifacts(artifact -> {
            // get group id from artifact
            String artifactGroupId = artifact.getGroupId();

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
     * Iterate list of artifacts and check them using a given lambda. See {@link #findArtifact(String, String)} and
     * {@link #artifactsByGroupId(String, boolean)} for examples.
     *
     * @param consumer the artifact consumer (see {@link ArtifactConsumer} for details
     * @return an artifact or {@code null}
     */
    public static Artifact iterateArtifacts(ArtifactConsumer consumer) {
        // get service loader for ArtifactVersionService
        ServiceLoader<ArtifactVersionService> serviceLoader = ServiceLoader.load(ArtifactVersionService.class);

        // iterate all published service classes
        for (ArtifactVersionService versionService : serviceLoader) {
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

}
