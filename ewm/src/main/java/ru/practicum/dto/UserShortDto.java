package ru.practicum.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
public class UserShortDto {
    Long id;
    String name;
}