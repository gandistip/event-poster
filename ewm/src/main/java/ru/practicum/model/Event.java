package ru.practicum.model;

import lombok.*;
import ru.practicum.model.enums.State;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    User initiator;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    @Column(name = "title")
    String title;

    @Column(name = "annotation")
    String annotation;          // Краткое описание события

    @Column(name = "event_date")
    LocalDateTime eventDate;

    @Column(name = "paid")
    Boolean paid;               // Платность события (true - платное)

    @Column(name = "description")
    String description;

    @JoinColumn(name = "confirmed_requests")
    Long confirmedRequests;     // Количество одобренных заявок на участие в данном событии

    @Column(name = "participant_limit")
    Long participantLimit;      // Ограничение на количество участников (0 - без ограничений)

    @Column(name = "request_moderation")
    Boolean requestModeration;  // Пре-модерация заявок на участие (true - требуется)

    @Column(name = "published_on")
    LocalDateTime publishedOn;

    @Column(name = "created_on")
    LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_state")
    State state;

    @Column(name = "views")
    Long views;
}
