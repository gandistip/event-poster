package ru.practicum.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Compilation;

import java.util.List;

public interface CompilationRepo extends JpaRepository<Compilation, Long> {

    List<Compilation> findByPinned(boolean pinned, PageRequest page);
}