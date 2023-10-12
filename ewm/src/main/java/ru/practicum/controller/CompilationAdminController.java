package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.CompilationNewDto;
import ru.practicum.dto.CompilationUpdateDto;
import ru.practicum.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationAdminController {

    private final CompilationService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompilationDto addCompilation(
            @RequestBody @Valid CompilationNewDto dto) {
        log.info("Подборку добавить {}", dto);
        return service.addCompilation(dto);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CompilationDto updateCompilation(
            @RequestBody @Valid CompilationUpdateDto dto,
            @PathVariable long compId) {
        log.info("Подборку обновить с id={}", compId);
        return service.updateCompilation(compId, dto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCompilation(
            @PathVariable("compId") long compId) {
        log.info("Подборку удалить с id={}", compId);
        service.deleteCompilation(compId);
    }
}