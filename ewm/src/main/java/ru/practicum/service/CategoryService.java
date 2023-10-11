package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CategoryDto;
import ru.practicum.exception.ConflictExc;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.model.Category;
import ru.practicum.repository.EventRepo;
import ru.practicum.repository.empty.CategoryRepo;
import ru.practicum.util.UtilService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;
    private final EventRepo eventRepo;
    private final UtilService utilService;

    @Transactional
    public CategoryDto addCategory(CategoryDto dto) {
        Category category = CategoryMapper.toCategory(dto);
        categoryRepo.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    public CategoryDto updateCategory(CategoryDto dto, long categoryId) {
        Category category = utilService.getCategoryIfExist(categoryId);
        category.setName(dto.getName());
        categoryRepo.save(category);
        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    public void deleteCategory(long categoryId) {
        utilService.getCategoryIfExist(categoryId);
        if (!eventRepo.findByCategoryId(categoryId).isEmpty()) {
            throw new ConflictExc("Удаление категории с привязанными событиями - невозможно");
        }
        categoryRepo.deleteById(categoryId);
    }

    public CategoryDto getCategoryById(long categoryId) {
        return CategoryMapper.toCategoryDto(utilService.getCategoryIfExist(categoryId));
    }

    public List<CategoryDto> getCategories(int from, int size) {
        return CategoryMapper.toCategoryDtoList(categoryRepo.findAll(UtilService.toPage(from, size)));
    }
}