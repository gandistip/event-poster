package ru.practicum.service.interf;

import org.springframework.data.domain.PageRequest;
import ru.practicum.dto.*;
import ru.practicum.model.filters.EventPublicFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventPublicService {

    EventFullDto getEventByIdPublic(long eventId, String uri, String ip);

    List<EventShortDto> getEventsByFilterPublic(EventPublicFilter filter, String uri, String ip, int from, int size);

}