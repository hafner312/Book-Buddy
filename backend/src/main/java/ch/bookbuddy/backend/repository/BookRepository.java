package ch.bookbuddy.backend.repository;

import ch.bookbuddy.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository-Interface für {@link Book}-Entitäten.
 * <p>
 * Bietet CRUD-Funktionalität für Bücher durch Erweiterung von {@link JpaRepository}.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByOwnerId(String ownerId);

    Optional<Book> findByIdAndOwnerId(Long id, String ownerId);

    boolean existsByOwnerId(String ownerId);
}
