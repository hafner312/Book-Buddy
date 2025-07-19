package ch.bookbuddy.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Testklasse zur Überprüfung des Spring Application Contexts.
 * <p>
 * Wird verwendet, um sicherzustellen, dass die Anwendung korrekt startet.
 */
@SpringBootTest
class BookBuddyApplicationTests {

    /**
     * Prüft, ob der Spring Context ohne Fehler geladen werden kann.
     */
    @Test
    void contextLoads() {
        // Erfolgreiches Laden des Contexts gilt als Test bestanden.
    }
}
