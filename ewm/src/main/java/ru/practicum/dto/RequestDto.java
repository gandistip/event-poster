package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.model.enums.Status;

import java.time.LocalDateTime;

import static ru.practicum.util.UtilConstant.DATE_FORMAT;

@Data
@Builder
@Jacksonized
public class RequestDto {
    Long id;
    @JsonFormat(pattern = DATE_FORMAT)
    LocalDateTime created;
    Long event;
    Long requester;
    Status status;
}