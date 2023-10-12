package ru.practicum.model.enums;

public enum State {
    // Состояние жизненного цикла события
    PUBLISHED,  // опубликовано
    PENDING,    // в ожидании
    REJECTED,	// отклонено
    CANCELED;   // отменено инициатором
}