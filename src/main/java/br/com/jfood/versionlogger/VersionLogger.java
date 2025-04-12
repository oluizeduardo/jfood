package br.com.jfood.versionlogger;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * The application version controller.
 */
@Component
public class VersionLogger {

    /**
     * It logs the current application version.
     * <p>
     * Spring calls the methods annotated with @PostConstruct only once,
     * just after the initialization of bean properties.
     */
    @PostConstruct
    private void postConstruct() {
        final String CURRENT_NUMBER_VERSION = "1.0.0";
        final String CURRENT_DATE_VERSION = "12-Apr-2025";

        System.out.println("JFOOD - STARTING - Version: "
                + CURRENT_NUMBER_VERSION + " - " + CURRENT_DATE_VERSION);
    }

}
