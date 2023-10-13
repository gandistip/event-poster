package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.model.enums.CommentStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class CommentNewDto {
    @NotBlank
    @Size(min = 10, max = 100)
    String text;
    CommentStatus status;
}
