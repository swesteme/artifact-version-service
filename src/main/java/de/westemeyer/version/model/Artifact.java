package de.westemeyer.version.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Simple object used to keep artifact coordinates and version in one place.
 */
@Value
@AllArgsConstructor
@SuppressWarnings("checkstyle:VisibilityModifier")
public class Artifact {
    /** The groupId property of the artifact. */
    String groupId;

    /** The artifactId property of the artifact. */
    String artifactId;

    /** The version property of the artifact. */
    String version;

    /** Timestamp of build. */
    long timestamp;

    /** The (optional) artifact name. */
    String name;

    /** The (optional) description property of the artifact. */
    @EqualsAndHashCode.Exclude
    String description;

    /** The (optional) url property of the artifact. */
    @EqualsAndHashCode.Exclude
    String url;

    /**
     * Compatibility constructor for service objects that are generated with an initial version of the artifact version
     * service.
     * @param groupId    the groupId property of the artifact
     * @param artifactId the artifactId property of the artifact
     * @param version    the version property of the artifact
     * @param timestamp  timestamp of build
     * @param name       the (optional) artifact name
     */
    public Artifact(String groupId, String artifactId, String version, long timestamp, String name) {
        this(groupId, artifactId, version, timestamp, name, null, null);
    }

    /**
     * Format the build date and time using a given format and time zone.
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
