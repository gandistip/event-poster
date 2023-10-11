package ru.practicum.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class CompilationUpdateDto {
    List<Long> events;
    Boolean pinned;
    @Size(min = 1, max = 50)
    String title;
}