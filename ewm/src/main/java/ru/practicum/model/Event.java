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

    @Column
    String title;

    @Column
    String annotation;          // Краткое описание события

    @Column
    LocalDateTime eventDate;

    @Column
    Boolean paid;               // Платность события (true - платное)

    @Column
    String description;

    @JoinColumn
    Long confirmedRequests;     // Количество одобренных заявок на участие в данном событии

    @Column
    Long participantLimit;      // Ограничение на количество участников (0 - без ограничений)

    @Column
    Boolean requestModeration;  // Пре-модерация заявок на участие (true - требуется)

    @Column
    LocalDateTime publishedOn;

    @Column
    LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    @Column
    State state;

    @Column
    Long views;
}
