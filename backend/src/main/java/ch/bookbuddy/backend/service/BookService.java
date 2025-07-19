package ch.bookbuddy.backend.service;

import ch.bookbuddy.backend.model.Book;
import ch.bookbuddy.backend.model.Category;
import ch.bookbuddy.backend.repository.BookRepository;
import ch.bookbuddy.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Serviceklasse zur Verwaltung von Büchern und deren zugehörigen Kategorien.
 * <p>
 * Kapselt die Geschäftslogik zur Validierung und Speicherung von Buchdaten.
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    /**
     * Setter für BookRepository – primär für Unit-Tests mit Mockito gedacht.
     *
     * @param bookRepo das zu setzende BookRepository
     */
    public void setBookRepo(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    /**
     * Setter für CategoryRepository – primär für Unit-Tests mit Mockito gedacht.
     *
     * @param categoryRepo das zu setzende CategoryRepository
     */
    public void setCategoryRepo(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    /**
     * Speichert ein Buch mit einer bestehenden Kategorie.
     * <p>
     * Wenn die angegebene Kategorie nicht existiert, wird eine {@link IllegalArgumentException} geworfen.
     *
     * @param book das zu speichernde Buch
     * @return das gespeicherte Buch mit verknüpfter Kategorie
     */
    public Book saveBookWithCategory(Book book) {
        String categoryName = book.getCategory().getName();
        Category existingCategory = categoryRepo.findByName(categoryName);

        if (existingCategory == null) {
            throw new IllegalArgumentException("Kategorie existiert nicht: " + categoryName);
        }

        book.setCategory(existingCategory);
        return bookRepo.save(book);
    }

    /**
     * Aktualisiert ein bestehendes Buch anhand der ID.
     * <p>
     * Wenn das Buch oder die Kategorie nicht gefunden wird, wird eine entsprechende Ausnahme geworfen.
     *
     * @param id           ID des zu aktualisierenden Buchs
     * @param bookDetails  neue Daten für das Buch
     * @return das aktualisierte Buch
     */
    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepo.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Buch mit ID " + id + " nicht gefunden."));

        String categoryName = bookDetails.getCategory().getName();
        Category existingCategory = categoryRepo.findByName(categoryName);

        if (existingCategory == null) {
            throw new IllegalArgumentException("Kategorie existiert nicht: " + categoryName);
        }

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(existingCategory);

        return bookRepo.save(book);
    }

    /**
     * Löscht ein Buch anhand seiner ID.
     *
     * @param id ID des zu löschenden Buchs
     * @return {@code true}, wenn das Buch existierte und gelöscht wurde, sonst {@code false}
     */
    public boolean deleteBook(Long id) {
        if (!bookRepo.existsById(id)) {
            return false;
        }
        bookRepo.deleteById(id);
        return true;
    }

    /**
     * Gibt alle gespeicherten Bücher zurück.
     *
     * @return Iterable aller Bücher
     */
    public Iterable<Book> getAllBooks() {
        return bookRepo.findAll();
    }
}
