package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.*;
import ru.practicum.exception.ConflictExc;
import ru.practicum.exception.ValidationExc;
import ru.practicum.mapper.EventMapper;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.*;
import ru.practicum.model.enums.Status;
import ru.practicum.repository.EventRepo;
import ru.practicum.repository.RequestRepo;
import ru.practicum.repository.empty.LocationRepo;
import ru.practicum.util.UtilService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static ru.practicum.model.enums.State.*;
import static ru.practicum.model.enums.StateAction.CANCEL_REVIEW;
import static ru.practicum.model.enums.StateAction.SEND_TO_REVIEW;
import static ru.practicum.model.enums.Status.CONFIRMED;
import static ru.practicum.util.UtilConstant.MIN_EVENT_TIME;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EventPrivateService {

    private final EventRepo eventRepo;
    private final RequestRepo requestRepo;
    private final LocationRepo locationRepo;
    private final UtilService utilService;

    @Transactional
    public EventFullDto addEvent(long userId, EventNewDto eventNewDto) {
        if (eventNewDto.getEventDate().isBefore(LocalDateTime.now().minusHours(MIN_EVENT_TIME))) {
            throw new ValidationExc("Событие должно начаться не менее чем через 2 часа");
        }
        User user = utilService.getUserIfExist(userId);
        Category category = utilService.getCategoryIfExist(eventNewDto.getCategory());

        Location location = locationRepo.save(eventNewDto.getLocation());
        Event event = EventMapper.toEvent(eventNewDto, category, location, user);
        eventRepo.save(event);
        return EventMapper.toEventFullDto(event);
    }

    @Transactional
    public EventFullDto updateEvent(EventUpdateDto dto, long userId, long eventId) {
        User user = utilService.getUserIfExist(userId);
        Event event = utilService.getEventIfExist(eventId);

        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new ConflictExc("Изменение события не инициатором - невозможно");
        }

        if (event.getState().equals(PUBLISHED)) {
            throw new ConflictExc("Изменение статуса опубликованного события - невозможно");
        }
        if (dto.getStateAction() != null) {
            if (dto.getStateAction().equals(SEND_TO_REVIEW)) {
                event.setState(PENDING);
            }
            if (dto.getStateAction().equals(CANCEL_REVIEW)) {
                event.setState(CANCELED);
            }
        }

        Category category = null;
        if (dto.getCategory() != null) {
            category = utilService.getCategoryIfExist(dto.getCategory());
        }
        Event updatedEvent = EventMapper.toEventUpdate(event, dto, category);
        locationRepo.save(updatedEvent.getLocation());
        eventRepo.save(updatedEvent);

        return EventMapper.toEventFullDto(updatedEvent);
    }

    @Transactional
    public RequestsReportDto updateRequestStatus(RequestsNewStatusDto request, long userId, long eventId) {
        utilService.getUserIfExist(userId);
        Event event = utilService.getEventIfExist(eventId);

        if (!event.getRequestModeration()) {
            throw new ConflictExc("Пре-модерация заявок на участие в данном событии не требуется");
        }
        if (request.getStatus().equals(Status.REJECTED) &&
                requestRepo.findAllByIdInAndStatus(request.getRequestIds(), CONFIRMED).size() > 0) {
            throw new ConflictExc("Подтвержденные заявки на участие не могут быть отклонены");
        }
        if (request.getStatus().equals(CONFIRMED) &&
                event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictExc("Исчерпан лимит участников в событии");
        }

        RequestsReportDto requestsReport = RequestsReportDto.builder()
                .confirmedRequests(Collections.emptyList())
                .rejectedRequests(Collections.emptyList())
                .build();

        if (request.getStatus().equals(Status.REJECTED)) {
            List<Long> rejected = request.getRequestIds();
            requestRepo.updateRequests(rejected, Status.REJECTED, Status.PENDING);
            requestsReport.setRejectedRequests(RequestMapper.toRequestDtoList(
                    requestRepo.findAllByIdInAndStatus(rejected, Status.REJECTED)));
        }
        if (request.getStatus().equals(CONFIRMED)) {
            List<Long> confirmed = request.getRequestIds();
            requestRepo.updateRequests(confirmed, CONFIRMED, Status.PENDING);
            event.setConfirmedRequests(event.getConfirmedRequests() + confirmed.size());
            requestsReport.setConfirmedRequests(RequestMapper.toRequestDtoList(
                    requestRepo.findAllByIdInAndStatus(confirmed, CONFIRMED)));
        }

        return requestsReport;
    }

    public EventFullDto getEventById(long userId, long eventId) {
        utilService.getUserIfExist(userId);
        utilService.getEventIfExist(eventId);
        Event event = eventRepo.findByInitiatorIdAndId(userId, eventId);
        return EventMapper.toEventFullDto(event);
    }

    public List<EventShortDto> getAllEventByUserId(long userId, int from, int size) {
        utilService.getUserIfExist(userId);
        List<Event> events = eventRepo.findByInitiatorId(userId, UtilService.toPage(from, size));
        return EventMapper.toEventShortDtoList(events);
    }

    public List<RequestDto> getRequestsForEventAndUser(long userId, long eventId) {
        utilService.getUserIfExist(userId);
        utilService.getEventIfExist(eventId);
        List<Request> requests = requestRepo.findByEventId(eventId);
        return RequestMapper.toRequestDtoList(requests);
    }
}