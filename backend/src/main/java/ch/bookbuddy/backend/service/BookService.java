package ch.bookbuddy.backend.service;

import ch.bookbuddy.backend.model.Book;
import ch.bookbuddy.backend.model.Category;
import ch.bookbuddy.backend.repository.BookRepository;
import ch.bookbuddy.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Serviceklasse zur Verwaltung von Büchern und deren zugehörigen Kategorien.
 * <p>
 * Kapselt die Geschäftslogik zur Validierung und Speicherung von Buchdaten.
 * Alle Buchoperationen sind auf die uebergebene ownerId (siehe OwnerIdFilter)
 * skopiert, damit jeder Besucher der Live-Demo nur seine eigene Bibliothek
 * sieht und veraendert.
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
     * @param book    das zu speichernde Buch
     * @param ownerId Kennung des Besitzers (siehe OwnerIdFilter)
     * @return das gespeicherte Buch mit verknüpfter Kategorie
     */
    public Book saveBookWithCategory(Book book, String ownerId) {
        String categoryName = book.getCategory().getName();
        Category existingCategory = categoryRepo.findByName(categoryName);

        if (existingCategory == null) {
            throw new IllegalArgumentException("Kategorie existiert nicht: " + categoryName);
        }

        book.setCategory(existingCategory);
        book.setOwnerId(ownerId);
        return bookRepo.save(book);
    }

    /**
     * Aktualisiert ein bestehendes Buch anhand der ID.
     * <p>
     * Wenn das Buch oder die Kategorie nicht gefunden wird, wird eine entsprechende Ausnahme geworfen.
     *
     * @param id           ID des zu aktualisierenden Buchs
     * @param bookDetails  neue Daten für das Buch
     * @param ownerId      Kennung des Besitzers (siehe OwnerIdFilter)
     * @return das aktualisierte Buch
     */
    public Book updateBook(Long id, Book bookDetails, String ownerId) {
        Book book = bookRepo.findByIdAndOwnerId(id, ownerId)
            .orElseThrow(() -> new NoSuchElementException("Buch mit ID " + id + " nicht gefunden."));

        String categoryName = bookDetails.getCategory().getName();
        Category existingCategory = categoryRepo.findByName(categoryName);

        if (existingCategory == null) {
            throw new IllegalArgumentException("Kategorie existiert nicht: " + categoryName);
        }

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(existingCategory);
        if (bookDetails.getStatus() != null) {
            book.setStatus(bookDetails.getStatus());
        }
        book.setRating(bookDetails.getRating());
        book.setPages(bookDetails.getPages());
        book.setCurrentPage(bookDetails.getCurrentPage());
        book.setNotes(bookDetails.getNotes());

        return bookRepo.save(book);
    }

    /**
     * Löscht ein Buch anhand seiner ID.
     *
     * @param id      ID des zu löschenden Buchs
     * @param ownerId Kennung des Besitzers (siehe OwnerIdFilter)
     * @return {@code true}, wenn das Buch existierte und gelöscht wurde, sonst {@code false}
     */
    public boolean deleteBook(Long id, String ownerId) {
        return bookRepo.findByIdAndOwnerId(id, ownerId)
            .map(book -> {
                bookRepo.delete(book);
                return true;
            })
            .orElse(false);
    }

    /**
     * Gibt alle Bücher eines Besitzers zurück.
     *
     * @param ownerId Kennung des Besitzers (siehe OwnerIdFilter)
     * @return Liste der Bücher dieses Besitzers
     */
    public List<Book> getAllBooks(String ownerId) {
        return bookRepo.findByOwnerId(ownerId);
    }
}
