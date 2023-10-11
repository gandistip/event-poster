package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class CompilationNewDto {
    List<Long> events;
    Boolean pinned;
    @Size(min = 1, max = 50)
    @NotBlank
    String title;
}