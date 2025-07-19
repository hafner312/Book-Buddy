package ch.bookbuddy.backend.repository;

import ch.bookbuddy.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
