package ru.practicum.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.exception.ValidationExc;
import ru.practicum.model.*;
import ru.practicum.model.enums.State;
import ru.practicum.repository.empty.CategoryRepo;
import ru.practicum.repository.CompilationRepo;
import ru.practicum.repository.EventRepo;
import ru.practicum.exception.NotFoundExc;
import ru.practicum.repository.RequestRepo;
import ru.practicum.repository.UserRepo;

import java.time.LocalDateTime;

import static ru.practicum.util.UtilConstant.FORMATTER;

@Service
@RequiredArgsConstructor
public class UtilService {

    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final EventRepo eventRepo;
    private final RequestRepo requestRepo;
    private final CompilationRepo compilationRepo;

    public User getUserIfExist(Long userId) {
        return userRepo.findById(userId).orElseThrow(() ->
                new NotFoundExc("userId=" + userId));
    }

    public Category getCategoryIfExist(Long categoryId) {
        return categoryRepo.findById(categoryId).orElseThrow(() ->
                new NotFoundExc("categoryId=" + categoryId));
    }

    public Event getEventIfExist(Long eventId) {
        return eventRepo.findById(eventId).orElseThrow(() ->
                new NotFoundExc("eventId=" + eventId));
    }

    public Request getRequestIfExist(Long requestId) {
        return requestRepo.findById(requestId).orElseThrow(() ->
                new NotFoundExc("requestId=" + requestId));
    }

    public Compilation getCompilationIfExist(Long compilationId) {
        return compilationRepo.findById(compilationId).orElseThrow(() ->
                new NotFoundExc("compilationId=" + compilationId));
    }

    public static PageRequest toPage(int from, int size) {
        return PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
    }


}