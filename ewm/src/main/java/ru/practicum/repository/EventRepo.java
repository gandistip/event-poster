package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Event;
import ru.practicum.model.enums.State;
import ru.practicum.model.filters.EventAdminFilter;
import ru.practicum.model.filters.EventPublicFilter;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

    Event findByInitiatorIdAndId(long initiatorId, long eventId);
    List<Event> findByInitiatorId(long initiatorId, PageRequest page);
    List<Event> findByCategoryId(long categoryId);
    List<Event> findByIdIn(List<Long> events);

    @Query( "SELECT e " +
            "FROM Event AS e " +
            "WHERE " +
            "(e.initiator.id IN :#{#f.users} OR :#{#f.users} IS NULL) " +
            "AND (e.state IN :#{#f.states} OR :#{#f.states} IS NULL) " +
            "AND (e.category.id IN :#{#f.categories} OR :#{#f.categories} IS NULL) " +
            "AND e.eventDate BETWEEN :#{#f.rangeStart} AND :#{#f.rangeEnd}")
    List<Event> findEventsByAdminFromParam(@Param("f") EventAdminFilter f, PageRequest page);

    @Query( "SELECT e " +
            "FROM Event AS e " +
            "WHERE (e.state = 'PUBLISHED') " +
            "AND (:#{#f.text} IS NULL) OR (LOWER(e.annotation) LIKE %:#{#f.text}%) OR (LOWER(e.description) LIKE %:#{#f.text}%) OR (LOWER(e.title) LIKE %:#{#f.text}%) " +
            "AND (:#{#f.categories} IS NULL OR e.category.id IN :#{#f.categories}) " +
            "AND (:#{#f.paid} IS NULL OR e.paid = :#{#f.paid}) " +
            "AND (:#{#f.onlyAvailable} = FALSE OR e.confirmedRequests < e.participantLimit)" +
            "AND e.eventDate BETWEEN :#{#f.rangeStart} AND :#{#f.rangeEnd} " +
            "GROUP BY e.id " +
            "ORDER BY :#{#f.sort} ASC")
    List<Event> findEventsByPublicFromParam(@Param("f") EventPublicFilter f, PageRequest page);

}