package ru.practicum.model.filters;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class EventPublicFilter {
    private String text;
    private List<Long> categories;
    private boolean paid;
    private boolean onlyAvailable;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private String sort;
}
