package ru.practicum.exception;

public class NotFoundExc extends RuntimeException {

    public NotFoundExc(String message) {
        super(message);
    }
}