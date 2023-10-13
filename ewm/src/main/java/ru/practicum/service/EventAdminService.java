package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventUpdateDto;
import ru.practicum.exception.ConflictExc;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.enums.StateAction;
import ru.practicum.model.filters.EventAdminFilter;
import ru.practicum.repository.EventRepo;
import ru.practicum.repository.empty.LocationRepo;
import ru.practicum.util.UtilService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.model.enums.State.*;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EventAdminService {

    private final EventRepo eventRepo;
    private final LocationRepo locationRepo;
    private final UtilService utilService;

    @Transactional
    public EventFullDto updateEventAdmin(EventUpdateDto dto, long eventId) {
        Event event = utilService.getEventIfExist(eventId);

        if (!event.getState().equals(PENDING)) {
            throw new ConflictExc("Изменение статуса утвержденного события - невозможно");
        }
        if (dto.getStateAction() != null) {
            if (dto.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                if (event.getState().equals(PUBLISHED)) {
                    throw new ConflictExc("Изменение статуса опубликованного события - невозможно");
                }
                event.setState(PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            }
            if (dto.getStateAction().equals(StateAction.REJECT_EVENT)) {
                if (event.getState().equals(PUBLISHED)) {
                    throw new ConflictExc("Отклонение опубликованного события - невозможно");
                }
                event.setState(REJECTED);
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

    public List<EventFullDto> getEventsByFilterAdmin(EventAdminFilter eventAdminFilter, int from, int size) {
        List<Event> events = eventRepo.findEventsByAdminFromParam(eventAdminFilter, UtilService.toPage(from, size));
        return EventMapper.toEventFullDtoList(events);
    }
}
