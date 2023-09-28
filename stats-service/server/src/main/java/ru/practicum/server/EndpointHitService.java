package ru.practicum.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EndpointHitService {
    private final EndpointHitRepo repo;

    @Transactional
    public EndpointHitDto saveEndpointHit(EndpointHitDto dto) {
        return EndpointHitMapper.toEndpointHitDto(repo.save(EndpointHitMapper.toEndpointHit(dto)));
    }

    @Transactional
    public List<ViewStatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        return unique ? repo.findViewStatsUniqIp(start, end, uris) : repo.findViewStats(start, end, uris);
    }
}