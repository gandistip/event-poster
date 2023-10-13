package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentNewDto;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.model.enums.CommentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommentMapper {

    public Comment toComment(CommentNewDto commentNewDto, User user, Event event) {
        return Comment.builder()
                .user(user)
                .event(event)
                .text(commentNewDto.getText())
                .createdTime(LocalDateTime.now())
                .status(CommentStatus.CREATED)
                .build();
    }

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .user(UserMapper.toUserDto(comment.getUser()))
                .event(EventMapper.toEventShortDto(comment.getEvent()))
                .text(comment.getText())
                .createdTime(comment.getCreatedTime())
                .status(comment.getStatus())
                .build();
    }

    public List<CommentDto> toCommentDtoList(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toCommentDto).collect(Collectors.toList());
    }
}