package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.EndpointHitDto;
import ru.practicum.StatsClient;
import ru.practicum.ViewStatsDto;
import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.exception.NotFoundExc;
import ru.practicum.exception.ValidationExc;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.Event;
import ru.practicum.model.filters.EventPublicFilter;
import ru.practicum.repository.EventRepo;
import ru.practicum.service.interf.EventPublicService;
import ru.practicum.util.UtilService;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.model.enums.State.PUBLISHED;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {

    private final EventRepo eventRepo;
    private final UtilService utilService;
    private final StatsClient statsClient;

    @Override
    public EventFullDto getEventByIdPublic(long eventId, String uri, String ip) {
        Event event = utilService.getEventIfExist(eventId);
        if (!event.getState().equals(PUBLISHED)) {
            throw new NotFoundExc("Просмотреть неопубликованное событие - нельзя, eventId=" + eventId);
        }
        event.setViews(getViewStats(event));
        eventRepo.save(event);
        addEndpointHit(uri, ip);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public List<EventShortDto> getEventsByFilterPublic(EventPublicFilter filter, String uri, String ip, int from, int size) {

        if (filter.getRangeStart() != null && filter.getRangeEnd() != null) {
            if (filter.getRangeStart().isAfter(filter.getRangeEnd())) {
                throw new ValidationExc("Дата начала не может быть позже даты окончания");
            }
        } else {
            filter.setRangeStart(LocalDateTime.now());
            filter.setRangeStart(LocalDateTime.now().plusYears(100));
        }
        if (filter.getText() != null) {
            filter.setText(filter.getText().toLowerCase());
        }
        if (filter.getSort() != null) {
            if (filter.getSort().equals("EVENT_DATE")) {
                filter.setSort("eventDate");
            } else if (filter.getSort().equals("VIEWS")) {
                filter.setSort("views");
            } else {
                throw new ValidationExc("Невозможно отсортировать по: " + filter.getSort());
            }
        } else {
            filter.setSort("id");
        }

        List<Event> events = eventRepo.findEventsByPublicFromParam(filter, UtilService.toPage(from, size));
        addEndpointHit(uri, ip);
        return EventMapper.toEventShortDtoList(events);
    }

    private void addEndpointHit(String uri, String ip) {
        statsClient.addEndpointHit(EndpointHitDto.builder()
                .app("ewm-service")
                .ip(ip)
                .uri(uri)
                .timestamp(LocalDateTime.now())
                .build());
    }

    private Long getViewStats(Event event) {
        String uri = "/events/" + event.getId();
        List<ViewStatsDto> viewStatsDto = statsClient.getViewStats(event.getCreatedOn(), LocalDateTime.now().plusSeconds(1), uri, true);
        if (viewStatsDto.size() > 0) {
            return viewStatsDto.get(0).getHits();
        }
        return 0L;
    }
}
