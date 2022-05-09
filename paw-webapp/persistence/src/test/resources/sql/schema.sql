drop table if exists customer cascade;
drop table if exists restaurant cascade;
drop table if exists dish cascade;
drop table if exists reservation cascade;
drop table if exists orderItem cascade;

CREATE TABLE IF NOT EXISTS users (
    userId SERIAL PRIMARY KEY,
    username varchar(100) UNIQUE,
    password varchar(100),
    role varchar(100) default 'ROLE_RESTAURANT'
    );


CREATE TABLE IF NOT EXISTS customer (
    customerId SERIAL PRIMARY KEY,
    customerName varchar(50) NOT NULL,
    Phone varchar(50) NOT NULL,
    Mail varchar(50),
    userId int,
    points int default 0,
    FOREIGN KEY (userId) REFERENCES users (userId)
    );


CREATE TABLE IF NOT EXISTS restaurant (
    restaurantId SERIAL PRIMARY KEY,
    restaurantName varchar(100) NOT NULL,
    phone varchar(100) NOT NULL,
    Mail varchar(50) NOT NULL,
    totalChairs int default 10 NOT NULL,
    openHour int DEFAULT 0,
    closeHour int default 0,
    userId int default 1,
    FOREIGN KEY (userId) REFERENCES users (userId)
    );

INSERT INTO restaurant (restaurantName, phone, Mail, totalChairs, openHour, closeHour, userId)
VALUES ('Pepito masterchef', 541124557623, 'pepito@masterchef.com', 10, 10, 20, 1);


-- CREATE TABLE IF NOT EXISTS image
-- (
--     imageId serial PRIMARY KEY,
--     bitmap bytea
-- );

CREATE TABLE IF NOT EXISTS dish (
    dishId SERIAL PRIMARY KEY,
    restaurantId int NOT NULL,
    dishName varchar(100) NOT NULL,
    price int NOT NULL,
    dishDescription varchar(200) NOT NULL,
    imageId int default 1 not null,
    category varchar(100) default 'MAIN_DISH' not null,
    FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId)
    );


CREATE TABLE IF NOT EXISTS reservation (
    reservationId   SERIAL PRIMARY KEY,
    restaurantId    integer NOT NULL,
    customerId      integer NOT NULL,
    reservationHour integer default 0 NOT NULL,
    reservationStatus integer,
    qPeople integer default 1,
    reservationDiscount boolean default false not null,
    startedAtTime timestamp default now(),
    FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId),
    FOREIGN KEY (customerId) REFERENCES customer (customerId)
    );


CREATE TABLE IF NOT EXISTS orderItem
(
    id          serial PRIMARY KEY,
    dishId     integer NOT NULL,
    reservationId integer NOT NULL,
    unitPrice     decimal(12,2) NOT NULL,
    quantity      integer NOT NULL,
    status        integer NOT NULL,
    FOREIGN KEY ( reservationId ) REFERENCES reservation ( reservationId ) ON DELETE CASCADE ,
    FOREIGN KEY ( dishId ) REFERENCES dish ( dishId ) ON DELETE CASCADE
    );


