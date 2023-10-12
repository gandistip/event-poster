package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserShortDto {
    Long id;
    String name;
}