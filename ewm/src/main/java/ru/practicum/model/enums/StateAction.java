package ru.practicum.model.enums;

public enum StateAction {   // Действие изменения состояния события

    // Административные:
    PUBLISH_EVENT,  // опубликовать
    REJECT_EVENT,   // отклонить

    // Инициатора:
    SEND_TO_REVIEW, // отправить на рассмотрение
    CANCEL_REVIEW   // отменить
}