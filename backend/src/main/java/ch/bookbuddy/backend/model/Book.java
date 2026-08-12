package ch.bookbuddy.backend.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
     * Lesestatus des Buches. Neu angelegte Bücher starten standardmäßig auf der Leseliste.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadingStatus status = ReadingStatus.WANT_TO_READ;

    /**
     * Persönliche Bewertung (0–5 Sterne), optional, nur sinnvoll bei fertig gelesenen Büchern.
     */
    @Min(value = 0, message = "Bewertung muss zwischen 0 und 5 liegen.")
    @Max(value = 5, message = "Bewertung muss zwischen 0 und 5 liegen.")
    private Integer rating;

    /**
     * Gesamtzahl Seiten des Buches, optional.
     */
    @Min(value = 1, message = "Seitenzahl muss mindestens 1 sein.")
    private Integer pages;

    /**
     * Aktuell gelesene Seite, optional – Basis für die Fortschrittsanzeige beim Lesen.
     */
    @Min(value = 0, message = "Aktuelle Seite darf nicht negativ sein.")
    private Integer currentPage;

    /**
     * Persönliche Notizen/Eindrücke zum Buch, optional.
     */
    @Size(max = 2000, message = "Notizen dürfen maximal 2000 Zeichen lang sein.")
    @Column(length = 2000)
    private String notes;

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

    public ReadingStatus getStatus() {
        return status;
    }

    public void setStatus(ReadingStatus status) {
        this.status = status;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
