package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentNewDto;
import ru.practicum.service.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentPrivateController {

    private final CommentService service;

    @PostMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentDto addComment(
            @RequestBody @Valid CommentNewDto dto,
            @PathVariable long userId,
            @PathVariable long eventId) {
        log.info("Комментарий добавить для события с id={}", eventId);
        return service.addComment(userId, eventId, dto);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentDto updateComment(
            @Valid @RequestBody CommentNewDto dto,
            @PathVariable long userId,
            @PathVariable long commentId) {
        log.info("Комментарий обновить с id={}", commentId);
        return service.updateComment(userId, commentId, dto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable long userId,
            @PathVariable long commentId) {
        log.info("Комментарий удалить с id={}", commentId);
        service.deleteComment(userId, commentId);
    }
}