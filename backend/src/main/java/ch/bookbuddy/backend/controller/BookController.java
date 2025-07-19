package ch.bookbuddy.backend.controller;

import ch.bookbuddy.backend.model.Book;
import ch.bookbuddy.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST-Controller zur Verwaltung von Büchern.
 * <p>
 * Dieser Controller stellt Endpunkte bereit, um Bücher zu erstellen, abzurufen, zu aktualisieren und zu löschen.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Gibt alle gespeicherten Bücher zurück.
     *
     * @return eine Liste aller Bücher
     */
    @GetMapping
    public Iterable<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * Erstellt ein neues Buch, sofern die zugehörige Kategorie vorhanden ist.
     *
     * @param book das Buchobjekt, das gespeichert werden soll
     * @return das erstellte Buch als HTTP-Response
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.saveBookWithCategory(book);
        return ResponseEntity.ok(savedBook);
    }

    /**
     * Aktualisiert ein bestehendes Buch anhand der ID.
     *
     * @param id          die ID des zu aktualisierenden Buchs
     * @param bookDetails die neuen Buchdaten
     * @return das aktualisierte Buch als HTTP-Response
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Löscht ein Buch anhand der ID.
     *
     * @param id die ID des zu löschenden Buchs
     * @return 204 No Content, wenn erfolgreich gelöscht; 404 Not Found, wenn nicht vorhanden
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
