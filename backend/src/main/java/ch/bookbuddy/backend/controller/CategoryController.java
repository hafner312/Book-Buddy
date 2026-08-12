package ch.bookbuddy.backend.controller;

import ch.bookbuddy.backend.model.Category;
import ch.bookbuddy.backend.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST-Controller für Kategorien.
 * <p>
 * Bietet Endpunkte zum Abrufen aller verfügbaren Kategorien sowie zur Initialisierung
 * eines vordefinierten Kategoriesatzes. Dieser Controller ist primär für die Verwaltung
 * der Buchkategorien im System zuständig.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryRepository categoryRepo;

    /**
     * Gibt alle vorhandenen Kategorien zurück.
     *
     * @return eine Liste aller Kategorien im System
     */
    @GetMapping
    public Iterable<Category> getAllCategories() {
        logger.info("GET /api/categories aufgerufen.");
        return categoryRepo.findAll();
    }

    /**
     * Legt eine neue Kategorie an, sofern noch keine mit demselben Namen existiert.
     *
     * @param category das Kategorie-Objekt mit dem gewünschten Namen
     * @return die neu angelegte Kategorie, oder die bereits existierende bei Namenskollision
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@org.springframework.web.bind.annotation.RequestBody Category category) {
        Category existing = categoryRepo.findByName(category.getName());
        if (existing != null) {
            return ResponseEntity.ok(existing);
        }
        Category saved = categoryRepo.save(new Category(category.getName()));
        logger.info("Neue Kategorie angelegt: {}", saved.getName());
        return ResponseEntity.ok(saved);
    }

    /**
     * Initialisiert einen vordefinierten Satz von Kategorien.
     * <p>
     * Diese Methode kann verwendet werden, um das System einmalig mit Standardkategorien
     * zu befüllen. Bereits vorhandene Kategorien werden nicht erneut gespeichert.
     * <p>
     * ⚠️ Hinweis: Sollte nach der Initialbefüllung deaktiviert oder geschützt werden.
     *
     * @return eine ResponseEntity mit einer Zusammenfassung der erstellten Kategorien oder einem Hinweis,
     *         dass bereits alle vorhanden waren
     */
    @PostMapping("/init")
    public ResponseEntity<?> initDefaultCategories() {
        List<String> names = List.of(
            "Roman", "Thriller", "Krimi", "Fantasy", "Science Fiction", "Biografie",
            "Autobiografie", "Sachbuch", "Ratgeber", "Psychologie", "Philosophie",
            "Kinderbuch", "Jugendbuch", "Märchen", "Comic", "Manga", "Historisch",
            "Drama", "Abenteuer", "Lyrik", "Religion", "Reise", "Wissenschaft", "Kunst", "Kochbuch"
        );

        List<String> created = new ArrayList<>();

        for (String name : names) {
            if (categoryRepo.findByName(name) == null) {
                Category c = new Category();
                c.setName(name);
                categoryRepo.save(c);
                created.add(name);
            }
        }

        if (created.isEmpty()) {
            logger.info("Keine neuen Kategorien hinzugefügt. Alle waren bereits vorhanden.");
            return ResponseEntity.ok("Alle Kategorien waren bereits vorhanden.");
        } else {
            logger.info("Neue Kategorien hinzugefügt: {}", created);
            return ResponseEntity.ok("Folgende Kategorien wurden neu angelegt: " + String.join(", ", created));
        }
    }
}
