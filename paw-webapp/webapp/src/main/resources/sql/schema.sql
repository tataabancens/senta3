CREATE TABLE IF NOT EXISTS customer (
  customerId SERIAL PRIMARY KEY,
  customerName varchar(50) NOT NULL,
  Phone varchar(50) NOT NULL,
  Mail varchar(50)
);

DROP TABLE IF EXISTS restaurant CASCADE;

CREATE TABLE IF NOT EXISTS restaurant (
  restaurantId SERIAL PRIMARY KEY,
  restaurantName varchar(100) NOT NULL,
  phone varchar(100) NOT NULL,
  Mail varchar(50) NOT NULL,
  Tables int NOT NULL
);

INSERT INTO restaurant(restaurantName, phone, Mail, Tables)
VALUES ('Pepito masterchef', 110502400, 'pepitococina@gmail.com', 3);

DROP TABLE IF EXISTS tables CASCADE;

CREATE TABLE IF NOT EXISTS tables (
    tableId SERIAL PRIMARY KEY,
    restaurantId int NOT NULL,
    hour int NOT NULL,
    FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId)
);

INSERT INTO tables(restaurantId, hour)
VALUES (1, 10);
INSERT INTO tables(restaurantId, hour)
VALUES (1, 10);
INSERT INTO tables(restaurantId, hour)
VALUES (1, 10);
INSERT INTO tables(restaurantId, hour)
VALUES (1, 11);


CREATE TABLE IF NOT EXISTS dish (
  dishId SERIAL PRIMARY KEY,
  restaurantId int NOT NULL,
  dishName varchar(100) NOT NULL,
  price int NOT NULL,
  dishDescription varchar(200) NOT NULL,
  FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId)
);

CREATE TABLE IF NOT EXISTS reservation (
    reservationId   SERIAL PRIMARY KEY,
    restaurantId    integer NOT NULL,
    customerId      integer NOT NULL,
    reservationDate timestamp,
    reservationStatus integer,
    FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId),
    FOREIGN KEY (customerId) REFERENCES customer (customerId)
);


CREATE TABLE IF NOT EXISTS users (
  userId SERIAL PRIMARY KEY,
  username varchar(100) UNIQUE,
  password varchar(100),
  role varchar(100)
);

CREATE TABLE IF NOT EXISTS orderItem
(
    id          serial PRIMARY KEY,
    dishId     integer NOT NULL,
    reservationId integer NOT NULL,
    unitPrice     decimal(12,2) NOT NULL,
    quantity      integer NOT NULL,
    status        integer NOT NULL,
    FOREIGN KEY ( reservationId ) REFERENCES reservation ( reservationId ),
    FOREIGN KEY ( dishId ) REFERENCES dish ( dishId )
);

CREATE TABLE IF NOT EXISTS pictures
(
    id serial PRIMARY KEY,
    bitmap bytea
);

INSERT INTO pictures (bitmap)
VALUES('\001'::bytea);

