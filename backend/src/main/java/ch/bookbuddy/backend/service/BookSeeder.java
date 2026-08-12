package ch.bookbuddy.backend.service;

import ch.bookbuddy.backend.model.Book;
import ch.bookbuddy.backend.model.ReadingStatus;
import ch.bookbuddy.backend.repository.BookRepository;
import ch.bookbuddy.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Befuellt die Bibliothek eines neuen Besuchers einmalig mit drei
 * Beispielbüchern, damit Dashboard und Bibliothek beim ersten Besuch nicht
 * leer sind. Wird pro ownerId (siehe OwnerIdFilter) einmalig ausgefuehrt -
 * jeder Besucher sieht seine eigene, unabhaengige Bibliothek.
 */
@Service
public class BookSeeder {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    public void seedIfNew(String ownerId) {
        if (bookRepo.existsByOwnerId(ownerId)) return;

        Book herrDerRinge = new Book("Der Herr der Ringe", "J.R.R. Tolkien", categoryRepo.findByName("Fantasy"));
        herrDerRinge.setOwnerId(ownerId);
        herrDerRinge.setStatus(ReadingStatus.READING);
        herrDerRinge.setPages(1200);
        herrDerRinge.setCurrentPage(450);
        herrDerRinge.setNotes("Sehr episch, freue mich auf den Rest.");

        Book sapiens = new Book("Sapiens: Eine kurze Geschichte der Menschheit", "Yuval Noah Harari", categoryRepo.findByName("Sachbuch"));
        sapiens.setOwnerId(ownerId);
        sapiens.setStatus(ReadingStatus.FINISHED);
        sapiens.setPages(528);
        sapiens.setRating(5);
        sapiens.setNotes("Absolut lesenswert, verändert die Perspektive.");

        Book neunzehnhundertvierundachtzig = new Book("1984", "George Orwell", categoryRepo.findByName("Roman"));
        neunzehnhundertvierundachtzig.setOwnerId(ownerId);
        neunzehnhundertvierundachtzig.setStatus(ReadingStatus.WANT_TO_READ);
        neunzehnhundertvierundachtzig.setPages(328);

        bookRepo.save(herrDerRinge);
        bookRepo.save(sapiens);
        bookRepo.save(neunzehnhundertvierundachtzig);
    }
}
