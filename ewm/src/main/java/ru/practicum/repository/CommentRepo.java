package ru.practicum.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    @Query("SELECT c " +
            "FROM Comment AS c " +
            "WHERE :eventId = c.event.id " +
            "AND c.status = 'CONFIRMED' " +
            "AND c.createdTime BETWEEN :rangeStart AND :rangeEnd")
    List<Comment> getCommentsByEventId(@Param("eventId") long eventId,
                                       @Param("rangeStart") LocalDateTime rangeStart,
                                       @Param("rangeEnd") LocalDateTime rangeEnd,
                                       PageRequest page);
}
