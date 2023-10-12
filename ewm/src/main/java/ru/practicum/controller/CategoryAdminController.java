package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CategoryDto;
import ru.practicum.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminController {

    private final CategoryService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryDto addCategory(
            @RequestBody @Valid CategoryDto dto) {
        log.info("Категорию добавить {} ", dto);
        return service.addCategory(dto);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CategoryDto updateCategory(
            @RequestBody @Valid CategoryDto dto,
            @PathVariable("catId") long categoryId) {
        log.info("Категорию обновить {} ", dto);
        return service.updateCategory(dto, categoryId);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(
            @PathVariable("catId") long categoryId) {
        log.info("Категорию удалить {} ", categoryId);
        service.deleteCategory(categoryId);
    }
}