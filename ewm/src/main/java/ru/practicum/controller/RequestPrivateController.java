package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.RequestDto;
import ru.practicum.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestPrivateController {

    private final RequestService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public RequestDto addRequest(
            @PathVariable long userId,
            @RequestParam long eventId) {
        log.info("Запрос на участие добавить с id={} пользователя с id={}", eventId, userId);
        return service.addRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(value = HttpStatus.OK)
    public RequestDto cancelRequest(
            @PathVariable long userId,
            @PathVariable long requestId) {
        log.info("Запрос на участие отменить с id={} для пользователя с id={}", requestId, userId);
        return service.cancelRequest(userId, requestId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<RequestDto> getRequestsByUserId(
            @PathVariable long userId) {
        log.info("Запросы на участие получить для пользователя с id={}", userId);
        return service.getRequestsByUserId(userId);
    }
}