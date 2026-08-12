package ch.bookbuddy.backend.controller;

import ch.bookbuddy.backend.filter.OwnerIdFilter;
import ch.bookbuddy.backend.model.Book;
import ch.bookbuddy.backend.service.BookSeeder;
import ch.bookbuddy.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * REST-Controller zur Verwaltung von Büchern.
 * <p>
 * Dieser Controller stellt Endpunkte bereit, um Bücher zu erstellen, abzurufen, zu aktualisieren und zu löschen.
 * Alle Operationen sind auf den anfragenden Besucher (siehe OwnerIdFilter) skopiert.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookSeeder bookSeeder;

    /**
     * Gibt alle Bücher des anfragenden Besuchers zurück.
     *
     * @return eine Liste aller Bücher dieses Besitzers
     */
    @GetMapping
    public List<Book> getAllBooks(HttpServletRequest request) {
        String ownerId = ownerId(request);
        bookSeeder.seedIfNew(ownerId);
        return bookService.getAllBooks(ownerId);
    }

    /**
     * Erstellt ein neues Buch, sofern die zugehörige Kategorie vorhanden ist.
     *
     * @param book das Buchobjekt, das gespeichert werden soll
     * @return das erstellte Buch als HTTP-Response
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book, HttpServletRequest request) {
        Book savedBook = bookService.saveBookWithCategory(book, ownerId(request));
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
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails,
                                            HttpServletRequest request) {
        Book updatedBook = bookService.updateBook(id, bookDetails, ownerId(request));
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Löscht ein Buch anhand der ID.
     *
     * @param id die ID des zu löschenden Buchs
     * @return 204 No Content, wenn erfolgreich gelöscht; 404 Not Found, wenn nicht vorhanden
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id, HttpServletRequest request) {
        boolean deleted = bookService.deleteBook(id, ownerId(request));
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private String ownerId(HttpServletRequest request) {
        return (String) request.getAttribute(OwnerIdFilter.REQUEST_ATTRIBUTE);
    }
}
