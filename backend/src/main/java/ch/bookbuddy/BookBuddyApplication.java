package ch.bookbuddy;

import ch.bookbuddy.backend.model.Category;
import ch.bookbuddy.backend.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hauptklasse der Spring Boot-Anwendung für BookBuddy.
 * <p>
 * Diese Klasse startet die gesamte Anwendung, registriert JPA-Repositories und
 * initialisiert die Datenbank mit Standardkategorien beim ersten Start.
 * Beispielbücher werden nicht mehr hier, sondern pro Besucher lazily durch
 * {@link ch.bookbuddy.backend.service.BookSeeder} angelegt (siehe dort) -
 * Kategorien bleiben dagegen bewusst global, da sie nur gemeinsame
 * Bezeichnungen und keine persönlichen Daten sind.
 */
@SpringBootApplication(scanBasePackages = {"ch.bookbuddy.backend"})
@EntityScan("ch.bookbuddy.backend.model")
@EnableJpaRepositories("ch.bookbuddy.backend.repository")
public class BookBuddyApplication {

    /**
     * Einstiegspunkt der Anwendung.
     *
     * @param args Argumente der Kommandozeile (werden von Spring Boot verwaltet)
     */
    public static void main(String[] args) {
        SpringApplication.run(BookBuddyApplication.class, args);
    }

    /**
     * Erstellt einen {@link CommandLineRunner}, der beim Start einmalig Standardkategorien in die Datenbank speichert.
     * <p>
     * Diese Methode prüft, ob jede Kategorie bereits existiert – falls nicht, wird sie gespeichert.
     *
     * @param repo das {@link CategoryRepository} zum Zugriff auf die Datenbank
     * @return eine Runner-Funktion, die beim Start ausgeführt wird
     */
    @Bean
    public CommandLineRunner seedCategories(CategoryRepository repo) {
        return args -> {
            String[] categories = {
                "Roman", "Thriller", "Krimi", "Fantasy", "Science Fiction", "Biografie",
                "Autobiografie", "Sachbuch", "Ratgeber", "Psychologie", "Philosophie",
                "Kinderbuch", "Jugendbuch", "Märchen", "Comic", "Manga", "Historisch",
                "Drama", "Abenteuer", "Lyrik", "Religion", "Reise", "Wissenschaft", "Kunst", "Kochbuch"
            };

            for (String name : categories) {
                if (repo.findByName(name) == null) {
                    Category c = new Category();
                    c.setName(name);
                    repo.save(c);
                }
            }
        };
    }
}
