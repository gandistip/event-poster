package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.service.EventPrivateService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class EventPrivateController {

    private final EventPrivateService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventFullDto addEvent(
            @RequestBody @Valid EventNewDto dto,
            @PathVariable long userId) {
        log.info("Событие добавить title={} пользователем с id={}", dto.getTitle(), userId);
        return service.addEvent(userId, dto);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EventFullDto updateEvent(
            @RequestBody @Valid EventUpdateDto dto,
            @PathVariable long userId,
            @PathVariable long eventId) {
        log.info("Событие обновить с id={} на событие c title={}", eventId, dto.getTitle());
        return service.updateEvent(dto, userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(value = HttpStatus.OK)
    private RequestsReportDto updateRequestStatus(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody RequestsNewStatusDto dto) {
        log.info("Запрос на участие в событии обновить с id={} пользователя с id={}", eventId, userId);
        return service.updateRequestStatus(dto, userId, eventId);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    public EventFullDto getEventById(
            @PathVariable long userId,
            @PathVariable long eventId) {
        log.info("Событие получить с id={} добавленное пользователем с id={}", eventId, userId);
        return service.getEventById(userId, eventId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<EventShortDto> getAllEventByUserId(
            @PathVariable long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("События получить добавленных пользователем с id={} с пагинацией", userId);
        return service.getAllEventByUserId(userId, from, size);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(value = HttpStatus.OK)
    private List<RequestDto> getRequestsForEventAndUser(
            @PathVariable long userId,
            @PathVariable long eventId) {
        log.info("Запросы на участие в событии получить с id={} пользователя с id={}", eventId, userId);
        return service.getRequestsForEventAndUser(userId, eventId);
    }
}