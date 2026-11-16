# Money Talks Project — Account Service

Микросервис счетов и исполнения ордеров **Money Talks Project**. Хранит счета
пользователей, их балансы и ордера, которые создаёт сервис транзакций.

## Ответственность

- создание счёта и выдача списка счетов пользователя;
- получение счёта по ID с проверкой владельца;
- выдача денежных ресурсов по списку ID для других сервисов;
- исполнение ордера: изменение балансов в транзакции;
- выдача статуса ордера.

## Стек

- Java 21, Spring Boot 4.0.1;
- Spring Web MVC, Spring Data JPA, Liquibase;
- PostgreSQL;
- Lombok, MapStruct, Maven.

## API

| Метод | Путь | Назначение |
|---|---|---|
| `GET` | `/accounts/all` | Все счета текущего пользователя |
| `POST` | `/accounts/create` | Создать счёт: `{title, description}` |
| `GET` | `/accounts/by-id?id=...` | Счёт по UUID |
| `POST` | `/accounts/resources` | Денежные ресурсы по списку ID: `{findIds:[...]}` |
| `POST` | `/orders/execute` | Исполнить ордер `{order:{...}}` |
| `POST` | `/orders/status` | Статус ордера `{order:{id}}` |

Пользователь определяется заголовком `X-User-ID`, который проставляет gateway.
Все запросы к счетам проверяют принадлежность счёта этому пользователю.

### Ордер

```json
{
  "order": {
    "id": "232161dc-8c9c-4ba6-9f85-dabed533dc06",
    "source": "be290141-8062-4aa0-820f-9a6a334df227",
    "target": "b4d54291-ac2b-44bb-bfea-bb531de08668",
    "amount": 30,
    "type": "TRANSFER"
  }
}
```

`type` — `REVENUE`, `EXPENSE` или `TRANSFER`:

| Тип | Что происходит |
|---|---|
| `REVENUE` | баланс целевого счёта увеличивается |
| `EXPENSE` | баланс исходного счёта уменьшается |
| `TRANSFER` | списание с исходного и зачисление на целевой |

ID ордера приходит от `mtp-backend-ms-transaction` и используется как первичный
ключ. Ордер сохраняется в статусе `CREATED`, затем пересчёт балансов выполняется в
отдельной транзакции с уровнем изоляции `SERIALIZABLE`, после чего ордер
переводится в `EXECUTED`. Для неизвестного ордера `/orders/status` возвращает
`UNREGISTERED`.

## Данные

Схема по умолчанию — `mtp_accs`, база — `mtp_dev`. Миграции Liquibase лежат в
`src/main/resources/db/dev`.

| Таблица | Назначение |
|---|---|
| `account` | `id` (UUID), `owner_id`, `title`, `description`, `amount` |
| `order` | `id` (UUID), `source`, `target`, `type`, `status`, `amount` |

Схема `mtp_accs` должна существовать до первого запуска.

## Конфигурация

| Переменная | Назначение | По умолчанию |
|---|---|---|
| `POSTGRES_URL` | Хост PostgreSQL | `localhost` |
| `POSTGRES_PORT` | Порт PostgreSQL | `5432` |
| `POSTGRES_DB_NAME` | Имя базы | `mtp_dev` |
| `POSTGRES_USER` | Пользователь БД | `postgres` |
| `POSTGRES_PASSWORD` | Пароль БД | dev-значение в `application.yml` |
| `DB_SCHEME_NAME` | Схема сервиса | `mtp_accs` |
| `LIQUIBASE_ENABLED` | Запускать миграции при старте | `true` |
| `LOGGING_LEVEL` | Уровень логирования | `debug` |

## Запуск

```bash
cd ../mtp-backend-root && ./mvnw install
cd ../mtp-backend-commons && ./mvnw install
./mvnw spring-boot:run
```

Сервис слушает порт `8082`. Проверка:

```bash
curl http://localhost:8082/accounts/all -H "X-User-ID: 1"
```

Тесты:

```bash
./mvnw test
```

В каталоге `http-requests` лежат примеры запросов. Они используют реальные UUID
из dev-базы и служат ручными сценариями, а не фикстурами.

## Известные ограничения

- `POST /orders/execute` не проверяет, исполнялся ли ордер раньше: повторный вызов
  для уже исполненного ордера снова изменит балансы. Защита от повтора живёт в
  `mtp-backend-ms-transaction`, который пропускает ордер со статусом `EXECUTED`.
- Сохранение ордера и пересчёт балансов выполняются в разных транзакциях: при
  сбое между ними ордер остаётся в `CREATED`, а балансы не изменены — это
  состояние штатно обрабатывается повторной отправкой ордера.
- Нет проверки достаточности средств: баланс счёта может уйти в минус.

## Связанные репозитории

- [Money-Talks-Project](https://github.com/Sun1tAr/Money-Talks-Project);
- [mtp-backend-ms-gateway](https://github.com/Sun1tAr/mtp-backend-ms-gateway);
- [mtp-backend-ms-transaction](https://github.com/Sun1tAr/mtp-backend-ms-transaction);
- [mtp-backend-commons](https://github.com/Sun1tAr/mtp-backend-commons).
