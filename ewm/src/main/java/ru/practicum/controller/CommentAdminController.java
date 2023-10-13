package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentNewDto;
import ru.practicum.service.CommentService;

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentAdminController {

    private final CommentService service;

    @DeleteMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCommentAdmin(
            @PathVariable long commentId) {
        log.info("Комментарий удалить с id={}", commentId);
        service.deleteCommentAdmin(commentId);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentDto updateCommentStatusAdmin(
            @RequestBody CommentNewDto dto,
            @PathVariable long commentId) {
        log.info("Комментария статус обновить с id={}", commentId);
        return service.updateCommentStatusAdmin(dto, commentId);
    }
}