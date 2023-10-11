package ru.practicum.repository.empty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
}