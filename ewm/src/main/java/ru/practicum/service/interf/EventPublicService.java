package ru.practicum.service.interf;

import ru.practicum.dto.EventFullDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.model.filters.EventPublicFilter;

import java.util.List;

public interface EventPublicService {

    EventFullDto getEventByIdPublic(long eventId, String uri, String ip);

    List<EventShortDto> getEventsByFilterPublic(EventPublicFilter filter, String uri, String ip, int from, int size);

}