# Event Poster - веб-сервис Афиша мероприятий
Java, Spring Boot, PostgreSQL, JPA, Hibernate ORM, Maven, Lombok, Docker, REST API.

## О проекте
Пользователи предлагают мероприятия, могут искать мероприятия по параметрам или формируя подборки и подают заявки на участие мероприятиях.
![](https://github.com/gandistip/event-poster/assets/120060980/5c46a985-a224-4696-872b-ca63867aa3c4)
### Микросервисная архитектура
Помимо основного сервиса имеется отдельный сервис сбора статистики фиксирующий:
   - количество обращений пользователей к спискам мероприятий,
   - количество запросов к подробной информации о мероприятии.

## Эндпоинты
   - публичные - доступны без регистрации любому пользователю сети,
   - закрытые - доступны только авторизованным пользователям,
   - административные - для администраторов сервиса.
![](https://github.com/gandistip/event-poster/assets/120060980/05777b80-b016-49a3-9b67-cd40cec03bbd)
### Подробная API спецификация:
* основного сервиса - https://github.com/gandistip/event-poster/blob/main/ewm-main-service-spec.json
* сервиса статистики - https://github.com/gandistip/event-poster/blob/main/ewm-stats-service-spec.json

## Основная сущность - Event (мероприятие)
![](https://github.com/gandistip/event-poster/assets/120060980/e2a53700-3c1b-461f-8aec-2e82bf29f1f7)

## Схема БД:
![](https://github.com/gandistip/java-explore-with-me/assets/120060980/7e77be41-4a12-42a3-ac60-df0d57c0cd0f)

