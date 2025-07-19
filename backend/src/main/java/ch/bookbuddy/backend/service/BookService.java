package ch.bookbuddy.backend.service;

import ch.bookbuddy.backend.model.Book;
import ch.bookbuddy.backend.model.Category;
import ch.bookbuddy.backend.repository.BookRepository;
import ch.bookbuddy.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    // --> Setter-Methoden für Tests (Mockito)
    public void setBookRepo(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public void setCategoryRepo(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public Book saveBookWithCategory(Book book) {
        String categoryName = book.getCategory().getName();
        Category existingCategory = categoryRepo.findByName(categoryName);

        if (existingCategory == null) {
            throw new IllegalArgumentException("Kategorie existiert nicht: " + categoryName);
        }

        book.setCategory(existingCategory);
        return bookRepo.save(book);
    }

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

    public boolean deleteBook(Long id) {
        if (!bookRepo.existsById(id)) {
            return false;
        }
        bookRepo.deleteById(id);
        return true;
    }

    public Iterable<Book> getAllBooks() {
        return bookRepo.findAll();
    }
}
