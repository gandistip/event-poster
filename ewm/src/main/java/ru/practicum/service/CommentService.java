package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.CommentNewDto;
import ru.practicum.exception.ConflictExc;
import ru.practicum.exception.ValidationExc;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.model.enums.CommentStatus;
import ru.practicum.repository.CommentRepo;
import ru.practicum.util.UtilService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final UtilService util;

    @Transactional
    public CommentDto addComment(long userId, long eventId, CommentNewDto dto) {
        User user = util.getUserIfExist(userId);
        Event event = util.getEventIfExist(eventId);

        Comment comment = CommentMapper.toComment(dto, user, event);

        comment = commentRepo.save(comment);
        return CommentMapper.toCommentDto(comment);
    }

    @Transactional
    public CommentDto updateComment(long userId, long commentId, CommentNewDto dto) {
        Comment comment = util.getCommentIfExist(commentId);

        if (userId != comment.getUser().getId()) {
            throw new ConflictExc("Редактировать комментарии могут только авторы");
        }
        if (dto.getText() != null && !dto.getText().isBlank()) {
            comment.setText(dto.getText());
        }

        comment = commentRepo.save(comment);
        return CommentMapper.toCommentDto(comment);
    }

    @Transactional
    public void deleteComment(long userId, long commentId) {
        Comment comment = util.getCommentIfExist(commentId);
        util.getUserIfExist(userId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new ConflictExc("Удалять комментарии могут только авторы");
        }
        commentRepo.deleteById(commentId);
    }

    @Transactional
    public void deleteCommentAdmin(long commentId) {
        util.getCommentIfExist(commentId);
        commentRepo.deleteById(commentId);
    }

    @Transactional
    public CommentDto updateCommentStatusAdmin(CommentNewDto dto, long commentId) {
        Comment comment = util.getCommentIfExist(commentId);

        if (!comment.getStatus().equals(CommentStatus.CREATED)) {
            throw new ConflictExc("Статус комментария уже утвержден");
        }

        if (dto.getStatus() != null) {
            if (dto.getStatus().equals(CommentStatus.CONFIRMED)) {
                comment.setStatus(CommentStatus.CONFIRMED);
            } else if (dto.getStatus().equals(CommentStatus.REJECTED)) {
                comment.setStatus(CommentStatus.REJECTED);
            } else {
                throw new ConflictExc("Передан несуществующий статус");
            }
        }

        commentRepo.save(comment);
        return CommentMapper.toCommentDto(comment);
    }

    public List<CommentDto> getCommentsByEventIdPublic(LocalDateTime start, LocalDateTime end, long eventId, int from, int size) {
        util.getEventIfExist(eventId);

        if (start != null && end != null) {
            if (start.isAfter(end)) {
                throw new ValidationExc("Некорректный фильтр времени");
            }
        }

        List<Comment> commentList = commentRepo.getCommentsByEventId(eventId, start, end, UtilService.toPage(from, size));
        return CommentMapper.toCommentDtoList(commentList);
    }
}