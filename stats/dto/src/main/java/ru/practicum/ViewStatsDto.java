package ru.practicum;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
public class ViewStatsDto {
    String app;
    String uri;
    Long hits;
}