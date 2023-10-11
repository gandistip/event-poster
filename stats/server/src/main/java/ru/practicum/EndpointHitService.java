package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.ValidationStatsExc;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EndpointHitService {

    private final EndpointHitRepo repo;

    @Transactional
    public EndpointHitDto addEndpointHit(EndpointHitDto dto) {
        return EndpointHitMapper.toEndpointHitDto(repo.save(EndpointHitMapper.toEndpointHit(dto)));
    }

    public List<ViewStatsDto> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new ValidationStatsExc("Время окончания - раньше времени начала");
        }
        return unique ? repo.findViewStatsUniqIp(start, end, uris) : repo.findViewStats(start, end, uris);
    }
}