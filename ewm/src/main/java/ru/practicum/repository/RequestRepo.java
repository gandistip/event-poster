package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Request;
import ru.practicum.model.enums.Status;

import java.util.List;

public interface RequestRepo extends JpaRepository<Request, Long> {

    Request findByRequesterIdAndEventId(long userId, long eventId);
    List<Request> findByRequesterId(long userId);
    List<Request> findByEventId(long eventId);
    List<Request> findAllByIdInAndStatus(List<Long> requestIds, Status status);

    @Modifying
    @Query( "UPDATE Request AS r " +
            "SET r.status = :newStatus " +
            "WHERE r.id IN :requestIds AND r.status = :status")
    void updateRequests(List<Long> requestIds, Status newStatus, Status status);
}