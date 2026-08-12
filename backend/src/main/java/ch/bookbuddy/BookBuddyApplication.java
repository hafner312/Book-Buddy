package ch.bookbuddy;

import ch.bookbuddy.backend.model.Book;
import ch.bookbuddy.backend.model.Category;
import ch.bookbuddy.backend.model.ReadingStatus;
import ch.bookbuddy.backend.repository.BookRepository;
import ch.bookbuddy.backend.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hauptklasse der Spring Boot-Anwendung für BookBuddy.
 * <p>
 * Diese Klasse startet die gesamte Anwendung, registriert JPA-Repositories und
 * initialisiert die Datenbank mit Standardkategorien beim ersten Start.
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
    @Order(1)
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

    /**
     * Befüllt eine leere Bücherliste einmalig mit drei Beispielbüchern in
     * unterschiedlichen Lesestatus, damit Dashboard und Bibliothek in der
     * Live-Demo nicht leer sind, sondern die Funktionen direkt zeigen.
     * <p>
     * Läuft nach {@link #seedCategories}, damit die benötigten Kategorien
     * bereits existieren.
     *
     * @param bookRepo     das {@link BookRepository} zum Zugriff auf die Bücher
     * @param categoryRepo das {@link CategoryRepository} zum Auflösen der Kategorienamen
     * @return eine Runner-Funktion, die beim Start ausgeführt wird
     */
    @Bean
    @Order(2)
    public CommandLineRunner seedBooks(BookRepository bookRepo, CategoryRepository categoryRepo) {
        return args -> {
            if (bookRepo.count() > 0) return;

            Book herrDerRinge = new Book("Der Herr der Ringe", "J.R.R. Tolkien", categoryRepo.findByName("Fantasy"));
            herrDerRinge.setStatus(ReadingStatus.READING);
            herrDerRinge.setPages(1200);
            herrDerRinge.setCurrentPage(450);
            herrDerRinge.setNotes("Sehr episch, freue mich auf den Rest.");

            Book sapiens = new Book("Sapiens: Eine kurze Geschichte der Menschheit", "Yuval Noah Harari", categoryRepo.findByName("Sachbuch"));
            sapiens.setStatus(ReadingStatus.FINISHED);
            sapiens.setPages(528);
            sapiens.setRating(5);
            sapiens.setNotes("Absolut lesenswert, verändert die Perspektive.");

            Book neunzehnhundertvierundachtzig = new Book("1984", "George Orwell", categoryRepo.findByName("Roman"));
            neunzehnhundertvierundachtzig.setStatus(ReadingStatus.WANT_TO_READ);
            neunzehnhundertvierundachtzig.setPages(328);

            bookRepo.save(herrDerRinge);
            bookRepo.save(sapiens);
            bookRepo.save(neunzehnhundertvierundachtzig);
        };
    }
}
