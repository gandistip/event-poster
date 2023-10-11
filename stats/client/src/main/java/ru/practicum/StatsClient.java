package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addEndpointHit(EndpointHitDto dto) {
        return post("/hit", dto);
    }

    public List<ViewStatsDto> getViewStats(LocalDateTime start, LocalDateTime end, String uris, boolean unique) {

        Map<String, Object> params = Map.of(
                "start", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                "end", end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                "unique", unique,
                "uris", uris
        );

        String url = "/stats?start={start}&end={end}&unique={unique}&uris={uris}";
        ViewStatsDto[] viewStatsDto = rest.getForObject(url, ViewStatsDto[].class, params);

        if (viewStatsDto != null) {
            return Arrays.stream(viewStatsDto).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }
}