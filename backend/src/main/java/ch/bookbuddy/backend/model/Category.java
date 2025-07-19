package ch.bookbuddy.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Repräsentiert eine Kategorie, der Bücher zugeordnet werden können.
 * <p>
 * Jede Kategorie hat einen eindeutigen Namen und wird intern über eine ID identifiziert.
 */
@Entity
public class Category {

    /**
     * Eindeutige ID der Kategorie (automatisch generiert).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Name der Kategorie.
     * Muss eindeutig und nicht leer sein, max. 100 Zeichen.
     */
    @NotBlank(message = "Name darf nicht leer sein.")
    @Size(max = 100, message = "Name darf maximal 100 Zeichen lang sein.")
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Standardkonstruktor für JPA.
     */
    public Category() {
    }

    /**
     * Konstruktor zur Initialisierung mit Name.
     *
     * @param name Name der Kategorie
     */
    public Category(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Vergleicht Kategorien anhand ihrer ID.
     *
     * @param o das zu vergleichende Objekt
     * @return true, wenn die IDs übereinstimmen
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    /**
     * Erzeugt den Hashcode basierend auf der ID.
     *
     * @return Hashcode-Wert
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Gibt eine String-Repräsentation der Kategorie zurück.
     *
     * @return String mit ID und Name
     */
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
