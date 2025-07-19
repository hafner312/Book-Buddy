package ch.bookbuddy.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Repräsentiert ein Buch innerhalb der Anwendung.
 * <p>
 * Jedes Buch hat einen Titel, einen Autor und ist genau einer {@link Category} zugeordnet.
 */
@Entity
public class Book {

    /**
     * Eindeutige ID des Buches (automatisch generiert).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Titel des Buches.
     * Darf nicht leer sein und maximal 150 Zeichen lang.
     */
    @NotBlank(message = "Titel darf nicht leer sein.")
    @Size(max = 150, message = "Titel darf maximal 150 Zeichen lang sein.")
    @Column(nullable = false)
    private String title;

    /**
     * Autor des Buches.
     * Darf nicht leer sein und maximal 100 Zeichen lang.
     */
    @NotBlank(message = "Autor darf nicht leer sein.")
    @Size(max = 100, message = "Autor darf maximal 100 Zeichen lang sein.")
    @Column(nullable = false)
    private String author;

    /**
     * Zugehörige Kategorie des Buches.
     * Muss gesetzt sein (nicht optional).
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    /**
     * Standardkonstruktor für JPA.
     */
    public Book() {}

    /**
     * Konstruktor mit allen Pflichtfeldern.
     *
     * @param title    Titel des Buches
     * @param author   Autor des Buches
     * @param category Kategorie, zu der das Buch gehört
     */
    public Book(String title, String author, Category category) {
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Vergleicht Bücher anhand ihrer ID.
     *
     * @param o das zu vergleichende Objekt
     * @return true, wenn IDs gleich sind
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    /**
     * Erzeugt den Hashcode basierend auf der Buch-ID.
     *
     * @return Hashcode-Wert
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Gibt eine stringbasierte Darstellung des Buchs zurück.
     *
     * @return String-Repräsentation
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category=" + (category != null ? category.getName() : null) +
                '}';
    }
}
