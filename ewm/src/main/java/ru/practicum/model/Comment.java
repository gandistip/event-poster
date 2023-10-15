package ru.practicum.model;

import lombok.*;
import ru.practicum.model.enums.CommentStatus;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;

    @Size(min = 10, max = 100)
    @Column
    String text;

    @Column
    LocalDateTime createdTime;

    @Column
    @Enumerated(EnumType.STRING)
    CommentStatus status;
}
