package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CommentDto;
import ru.practicum.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.UtilConstant.DATE_FORMAT;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentPublicController {

    private final CommentService service;

    @GetMapping("{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommentDto> getCommentsByEventIdPublic(
            @PathVariable long eventId,
            @RequestParam(defaultValue = "2000-01-01 00:00:00") @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime rangeStart,
            @RequestParam(defaultValue = "2100-01-01 00:00:00") @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Комментарии получить для события с id={}", eventId);
        return service.getCommentsByEventIdPublic(rangeStart, rangeEnd, eventId, from, size);
    }
}
