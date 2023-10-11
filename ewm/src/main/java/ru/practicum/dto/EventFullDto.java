package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.model.enums.State;
import ru.practicum.model.Location;

import java.time.LocalDateTime;

import static ru.practicum.util.UtilConstant.DATE_FORMAT;

@Data
@Builder
@Jacksonized
public class EventFullDto {
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    @JsonFormat(pattern = DATE_FORMAT)
    LocalDateTime createdOn;
    String description;
    @JsonFormat(pattern = DATE_FORMAT)
    LocalDateTime eventDate;
    Long id;
    UserShortDto initiator;
    Location location;
    Boolean paid;
    Long participantLimit;
    @JsonFormat(pattern = DATE_FORMAT)
    LocalDateTime publishedOn;
    Boolean requestModeration;
    State state;
    String title;
    Long views;
}