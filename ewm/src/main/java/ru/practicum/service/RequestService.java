package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.RequestDto;
import ru.practicum.exception.ConflictExc;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.Event;
import ru.practicum.model.Request;
import ru.practicum.model.User;
import ru.practicum.model.enums.State;
import ru.practicum.model.enums.Status;
import ru.practicum.repository.RequestRepo;
import ru.practicum.util.UtilService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class RequestService {

    private final RequestRepo requestRepo;
    private final UtilService utilService;

    @Transactional
    public RequestDto addRequest(long userId, long eventId) {
        User user = utilService.getUserIfExist(userId);
        Event event = utilService.getEventIfExist(eventId);

        Request request = requestRepo.findByRequesterIdAndEventId(userId, eventId);

        if (request != null) {
            throw new ConflictExc("Запрос на участие повторный - невозможен");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictExc("Запрос на участие в неопубликованном событии - невозможен");
        }
        if (Objects.equals(event.getInitiator().getId(), user.getId())) {
            throw new ConflictExc("Запрос на участие произведенный автором события - невозможен");
        }
        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictExc("Запрос на участие сверх лимита участников - невозможен");
        }

        Request newRequest = Request.builder()
                .event(event)
                .requester(user)
                .created(LocalDateTime.now())
                .status(Status.PENDING)
                .build();

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            newRequest.setStatus(Status.CONFIRMED);
        }

        return RequestMapper.toRequestDto(requestRepo.save(newRequest));
    }

    @Transactional
    public RequestDto cancelRequest(long userId, long requestId) {
        utilService.getUserIfExist(userId);
        Request request = utilService.getRequestIfExist(requestId);
        request.setStatus(Status.CANCELED);
        return RequestMapper.toRequestDto(requestRepo.save(request));
    }

    public List<RequestDto> getRequestsByUserId(long userId) {
        utilService.getUserIfExist(userId);
        List<Request> requestList = requestRepo.findByRequesterId(userId);
        return RequestMapper.toRequestDtoList(requestList);
    }
}