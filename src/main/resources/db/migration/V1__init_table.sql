CREATE TABLE Client
(
    id         int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    lastname   varchar(50)        NOT NULL,
    "name"     varchar(50)        NOT NULL,
    patronymic varchar(50),
    phone      varchar(13) UNIQUE NOT NULL,
    birthday   DATE,
    email      varchar(50) UNIQUE NOT NULL,
    password   varchar(50)        NOT NULL
);
CREATE TABLE Account
(
    account_id     int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    client_id      INT REFERENCES Client (id),
    account_number int UNIQUE NOT NULL,
    amount         int
);