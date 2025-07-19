package ch.bookbuddy.backend.repository;

import ch.bookbuddy.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository-Interface für {@link Book}-Entitäten.
 * <p>
 * Bietet CRUD-Funktionalität für Bücher durch Erweiterung von {@link JpaRepository}.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    // Optional: Eigene Query-Methoden wie findByTitle(...) können hier ergänzt werden.
}
