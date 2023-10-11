package ru.practicum.dto;

import lombok.*;
import ru.practicum.model.enums.Status;

import java.util.List;

@Data
@Builder
public class RequestsNewStatusDto {
    List<Long> requestIds;
    Status status;
}