package de.westemeyer.version.core.model;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Simple object used to keep artifact coordinates and version in one place.
 *
 * @param groupId        The groupId property of the artifact.
 * @param artifactId     The artifactId property of the artifact.
 * @param version        The version property of the artifact.
 * @param timestamp      Timestamp of build.
 * @param name           The (optional) artifact name.
 * @param description    The (optional) description property of the artifact.
 * @param url            The (optional) url property of the artifact.
 * @param parentArtifact The (optional) parent artifact.
 */
public record Artifact(@NonNull String groupId, @NonNull String artifactId, @NonNull String version, long timestamp,
                       String name, @EqualsAndHashCode.Exclude String description,
                       @EqualsAndHashCode.Exclude String url,
                       ArtifactCoordinates parentArtifact) implements ArtifactCoordinates {
    /**
     * Compatibility constructor for service objects that are generated with an initial version of the artifact version
     * service.
     *
     * @param groupId    the groupId property of the artifact
     * @param artifactId the artifactId property of the artifact
     * @param version    the version property of the artifact
     * @param timestamp  timestamp of build
     * @param name       the (optional) artifact name
     */
    public Artifact(@NonNull String groupId, @NonNull String artifactId, @NonNull String version, long timestamp, String name) {
        this(groupId, artifactId, version, timestamp, name, null, null, null);
    }

    /**
     * Format the build date and time using a given format and time zone.
     *
     * @param format the format to use for string conversion
     * @param zone   the time zone
     * @return time stamp formatted as string
     */
    public String formatTimestamp(String format, TimeZone zone) {
        // create formatter
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        // set the time zone
        formatter.setTimeZone(zone);

        // create date value from timestamp
        Date date = new Date(timestamp);

        // create formatted string
        return formatter.format(date);
    }
}
