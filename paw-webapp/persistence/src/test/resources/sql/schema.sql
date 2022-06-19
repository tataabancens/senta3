SET DATABASE SQL SYNTAX PGS TRUE;

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


-- CREATE TABLE IF NOT EXISTS image
-- (
--     imageId serial PRIMARY KEY,
--     bitmap bytea
-- );


CREATE TABLE IF NOT EXISTS dishcategory (
    id SERIAL PRIMARY KEY,
    name varchar(50),
    restaurant_id int,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (restaurantId)
    );

CREATE TABLE IF NOT EXISTS dish (
    dishId SERIAL PRIMARY KEY,
    restaurantId int NOT NULL,
    dishName varchar(100) NOT NULL,
    price int NOT NULL,
    dishDescription varchar(200) NOT NULL,
    imageId int default 1 not null,
    category_id int default 1 not null,
    FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId),
    FOREIGN KEY (category_id) REFERENCES dishcategory (id)
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
    reservationDate timestamp,
    tableNumber integer default 0,
    securityCode varchar(6),
    hand boolean default false NOT NULL,
    isToday boolean default false NOT NULL,
    FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId),
    FOREIGN KEY (customerId) REFERENCES customer (customerId)
    );
-- ALTER TABLE reservation ADD IF NOT EXISTS reservationDate timestamp;
-- ALTER TABLE reservation ADD IF NOT EXISTS tableNumber integer default 0;
-- ALTER TABLE reservation ADD COLUMN IF NOT EXISTS hand boolean default false NOT NULL;
-- ALTER TABLE reservation ADD COLUMN IF NOT EXISTS isToday boolean default false NOT NULL;


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

INSERT INTO users (userId, username, password)
VALUES (1, 'Pepito', '123');
INSERT INTO users (userId, username, password)
VALUES (2, 'Juancho', '123');
INSERT INTO users (userId, username, password)
VALUES (3, 'Camila', '123');

INSERT INTO customer (customerId, customerName, Phone, Mail, userId)
VALUES (1, 'Juancho', 541124557633, 'juan@gmail.com', 2);
INSERT INTO customer (customerId, customerName, Phone, Mail, userId)
VALUES (2, 'Camila', 541124557634, 'juan@gmail.com', 3);

INSERT INTO restaurant (restaurantName, phone, Mail, totalChairs, openHour, closeHour, userId)
VALUES ('Pepito masterchef', 541124557623, 'pepito@masterchef.com', 10, 10, 20, 1);

INSERT INTO dishcategory (id, name, restaurant_id)
VALUES (1, 'testCategory', 1);

INSERT INTO dish (dishId, restaurantId, dishName, price, dishDescription, imageId, category_id)
VALUES (1, 1, 'testFood', 100, 'testDescription', 1, 1);
INSERT INTO dish (dishId, restaurantId, dishName, price, dishDescription, imageId, category_id)
VALUES (2, 1, 'testFood2', 100, 'testDescription', 1, 1);
INSERT INTO dish (dishId, restaurantId, dishName, price, dishDescription, imageId, category_id)
VALUES (3, 1, 'testFood3', 100, 'testDescription', 1, 1);

INSERT INTO reservation(reservationId, restaurantId, customerId, reservationHour, reservationStatus, qPeople, reservationDiscount, startedAtTime, reservationDate, tableNumber, securityCode, hand, isToday)
VALUES (1, 1, 1, 12, 1, 1, false, now(), now(), 1, 'AAAAAA', false, true);
INSERT INTO reservation(reservationId, restaurantId, customerId, reservationHour, reservationStatus, qPeople, reservationDiscount, startedAtTime, reservationDate, tableNumber, securityCode, hand, isToday)
VALUES (2, 1, 2, 12, 1, 1, false, now(), now(), 2, 'BBBBBB', false, true);

INSERT INTO orderItem(id, dishId, reservationId, unitPrice, quantity, status)
VALUES (1, 1, 1, 100, 1, 1);
INSERT INTO orderItem(id, dishId, reservationId, unitPrice, quantity, status)
VALUES (2, 2, 1, 100, 1, 1);
INSERT INTO orderItem(id, dishId, reservationId, unitPrice, quantity, status)
VALUES (3, 1, 2, 100, 1, 0);





