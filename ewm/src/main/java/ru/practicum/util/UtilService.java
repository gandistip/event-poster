package ru.practicum.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundExc;
import ru.practicum.model.*;
import ru.practicum.repository.*;
import ru.practicum.repository.empty.CategoryRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UtilService {

    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final EventRepo eventRepo;
    private final RequestRepo requestRepo;
    private final CompilationRepo compilationRepo;
    private final CommentRepo commentRepo;

    public User getUserIfExist(long userId) {
        return userRepo.findById(userId).orElseThrow(() ->
                new NotFoundExc("userId=" + userId));
    }

    public Category getCategoryIfExist(long categoryId) {
        return categoryRepo.findById(categoryId).orElseThrow(() ->
                new NotFoundExc("categoryId=" + categoryId));
    }

    public Event getEventIfExist(long eventId) {
        return eventRepo.findById(eventId).orElseThrow(() ->
                new NotFoundExc("eventId=" + eventId));
    }

    public Request getRequestIfExist(long requestId) {
        return requestRepo.findById(requestId).orElseThrow(() ->
                new NotFoundExc("requestId=" + requestId));
    }

    public Compilation getCompilationIfExist(long compilationId) {
        return compilationRepo.findById(compilationId).orElseThrow(() ->
                new NotFoundExc("compilationId=" + compilationId));
    }

    public Comment getCommentIfExist(long commentId) {
        return commentRepo.findById(commentId).orElseThrow(() ->
                new NotFoundExc("commentId=" + commentId));
    }

    public static PageRequest toPage(int from, int size) {
        return PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
    }

    public LocalDateTime parseDate(String date) {
        if (date != null) {
            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            return null;
        }
    }
}