package de.westemeyer.version.model;

import java.util.Comparator;

/**
 * The default order of artifacts is alphanumerical comparison of groupId, artifactId and version.
 * Comparing versions can be done more in the way of respecting major, minor and path levels by
 * consumers implementing own comparators. However, ideally there will not be more than one version
 * of the same artifact on the classpath at the same time.
 */
public class DefaultArtifactComparator implements Comparator<Artifact> {
    @Override
    public int compare(Artifact o1, Artifact o2) {
        // compare group id part
        int comparisonResult = o1.getGroupId().compareTo(o2.getGroupId());
        if (comparisonResult != 0) {
            return comparisonResult;
        }

        // compare artifact id part
        comparisonResult = o1.getArtifactId().compareTo(o2.getArtifactId());
        if (comparisonResult != 0) {
            return comparisonResult;
        }

        // in the end: compare versions
        return o1.getVersion().compareTo(o2.getVersion());
    }
}
