package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.model.Location;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.util.UtilConstant.DATE_FORMAT;

@Data
@Builder
@Jacksonized
public class EventNewDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;
    @NotNull
    Long category;
    @NotBlank
    @Size(min = 20, max = 7000)
    String description;
    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = DATE_FORMAT)
    LocalDateTime eventDate;
    @NotNull
    Location location;
    @NotNull
    @Builder.Default
    Boolean paid = false;
    @Builder.Default
    Long participantLimit = 0L;
    @Builder.Default
    Boolean requestModeration = true;
    @NotBlank
    @Size(min = 3, max = 120)
    String title;
}