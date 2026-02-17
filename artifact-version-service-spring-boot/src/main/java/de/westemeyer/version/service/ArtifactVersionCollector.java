package de.westemeyer.version.service;

import de.westemeyer.version.core.api.ArtifactVersionService;
import de.westemeyer.version.core.collector.AbstractArtifactVersionCollector;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Collector service is used to aggregate all artifact POJOs into a Set of artifacts.
 */
@Service
@RequiredArgsConstructor
public class ArtifactVersionCollector extends AbstractArtifactVersionCollector {

    /**
     * List of services, injected by Spring.
     */
    @Getter
    private final List<ArtifactVersionService> artifactVersionServices;

}
