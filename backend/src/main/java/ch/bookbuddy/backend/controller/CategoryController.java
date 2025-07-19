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
 * Liefert alle verfügbaren Kategorien und erlaubt einmalige Initialisierung.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryRepository categoryRepo;

    /**
     * Gibt alle vorhandenen Kategorien zurück.
     */
    @GetMapping
    public Iterable<Category> getAllCategories() {
        logger.info("GET /api/categories aufgerufen.");
        return categoryRepo.findAll();
    }

    /**
     * Initialisiert vordefinierte Kategorien (einmaliger Setup-Aufruf).
     * ⚠️ Optional absichern oder nach Deployment entfernen!
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
