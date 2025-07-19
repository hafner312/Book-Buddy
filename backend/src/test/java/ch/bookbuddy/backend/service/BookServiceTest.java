package ch.bookbuddy.backend.service;

import ch.bookbuddy.backend.model.Book;
import ch.bookbuddy.backend.model.Category;
import ch.bookbuddy.backend.repository.BookRepository;
import ch.bookbuddy.backend.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookRepository bookRepo;
    private CategoryRepository categoryRepo;
    private BookService bookService;

    @BeforeEach
    void setup() {
        bookRepo = mock(BookRepository.class);
        categoryRepo = mock(CategoryRepository.class);
        bookService = new BookService();
        bookService.setBookRepo(bookRepo);
        bookService.setCategoryRepo(categoryRepo);
    }

    @Test
    void testSaveBookWithExistingCategory() {
        Category category = new Category();
        category.setName("Roman");

        Book book = new Book();
        book.setTitle("Testbuch");
        book.setAuthor("Autor");
        book.setCategory(category);

        when(categoryRepo.findByName("Roman")).thenReturn(category);
        when(bookRepo.save(book)).thenReturn(book);

        Book savedBook = bookService.saveBookWithCategory(book);

        assertNotNull(savedBook);
        verify(bookRepo, times(1)).save(book);
    }

    @Test
    void testSaveBookWithNonExistingCategoryThrowsException() {
        Category category = new Category();
        category.setName("Unbekannt");

        Book book = new Book();
        book.setTitle("Testbuch");
        book.setAuthor("Autor");
        book.setCategory(category);

        when(categoryRepo.findByName("Unbekannt")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.saveBookWithCategory(book);
        });

        assertEquals("Kategorie existiert nicht: Unbekannt", exception.getMessage());
    }

    @Test
    void testUpdateBookWithExistingCategory() {
        Category category = new Category();
        category.setName("Roman");

        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Alt");
        existingBook.setAuthor("Alt Autor");
        existingBook.setCategory(category);

        Book updatedBook = new Book();
        updatedBook.setTitle("Neu");
        updatedBook.setAuthor("Neu Autor");
        updatedBook.setCategory(category);

        when(bookRepo.findById(1L)).thenReturn(Optional.of(existingBook));
        when(categoryRepo.findByName("Roman")).thenReturn(category);
        when(bookRepo.save(any(Book.class))).thenReturn(existingBook);

        Book result = bookService.updateBook(1L, updatedBook);

        assertEquals("Neu", result.getTitle());
        assertEquals("Neu Autor", result.getAuthor());
        verify(bookRepo, times(1)).save(existingBook);
    }

    @Test
    void testDeleteBookWhenExists() {
        when(bookRepo.existsById(1L)).thenReturn(true);

        boolean result = bookService.deleteBook(1L);

        assertTrue(result);
        verify(bookRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBookWhenNotExists() {
        when(bookRepo.existsById(1L)).thenReturn(false);

        boolean result = bookService.deleteBook(1L);

        assertFalse(result);
        verify(bookRepo, never()).deleteById(anyLong());
    }
}
