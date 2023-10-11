package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class CompilationUpdateDto {
    List<Long> events;
    Boolean pinned;
    @Size(min = 1, max = 50)
    String title;
}