package br.com.jfood.service_registry_ms.versionlogger;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The application version controller.
 */
@Component
public class VersionLogger {

    private static final Logger logger = LoggerFactory.getLogger(VersionLogger.class);

    /**
     * It logs the current application version.
     * <p>
     * Spring calls the methods annotated with @PostConstruct only once,
     * just after the initialization of bean properties.
     */
    @PostConstruct
    private void postConstruct() {
        final String CURRENT_NUMBER_VERSION = "1.0.0";
        final String CURRENT_DATE_VERSION = "26-Apr-2025";

        logger.info("JFOOD - MS-SERVICE-REGISTRY - STARTING - Version: {} - {}", CURRENT_NUMBER_VERSION, CURRENT_DATE_VERSION);
    }

}
