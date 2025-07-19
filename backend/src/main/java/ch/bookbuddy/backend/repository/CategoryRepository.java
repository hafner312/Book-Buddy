package ch.bookbuddy.backend.repository;

import ch.bookbuddy.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name); // Rückgabe als Kategorie oder null
}
