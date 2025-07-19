package ch.bookbuddy.backend.repository;

import ch.bookbuddy.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository-Interface für {@link Category}-Entitäten.
 * <p>
 * Bietet CRUD-Funktionalität für Kategorien sowie eine benutzerdefinierte
 * Methode zum Suchen per Name.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Sucht eine Kategorie anhand ihres Namens.
     *
     * @param name der gesuchte Kategoriename
     * @return die gefundene Kategorie oder {@code null}, falls nicht vorhanden
     */
    Category findByName(String name);
}
