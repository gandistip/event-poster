package ru.practicum.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.model.enums.CommentStatus;

import java.time.LocalDateTime;

import static ru.practicum.util.UtilConstant.DATE_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    Long id;
    UserDto user;
    EventShortDto event;
    String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    LocalDateTime createdTime;
    CommentStatus status;
}