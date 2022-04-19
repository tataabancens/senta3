drop table if exists customer cascade;
drop table if exists restaurant cascade;
drop table if exists dish cascade;
drop table if exists reservation cascade;
drop table if exists orderItem cascade;
drop table if exists users cascade;

CREATE TABLE IF NOT EXISTS customer (
  customerId SERIAL PRIMARY KEY,
  customerName varchar(50) NOT NULL,
  Phone varchar(50) NOT NULL,
  Mail varchar(50)
);

INSERT INTO customer (customername, phone, Mail)
VALUES('Juan el loco','110502517', 'uriel.mihuraa@gmail.com');

CREATE TABLE IF NOT EXISTS restaurant (
  restaurantId SERIAL PRIMARY KEY,
  restaurantName varchar(100) NOT NULL,
  phone varchar(100) NOT NULL,
  Mail varchar(50) NOT NULL
);

INSERT INTO restaurant (restaurantName, phone, Mail)
VALUES('Pepito Masterchef','110502400', 'pepitococina@gmail.com');

CREATE TABLE IF NOT EXISTS dish (
  dishId SERIAL PRIMARY KEY,
  restaurantId int NOT NULL,
  dishName varchar(100) NOT NULL,
  price int NOT NULL,
  dishDescription varchar(200) NOT NULL,
  FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId)
);

INSERT INTO dish (restaurantId, dishName, price, dishDescription)
VALUES(1,'Milanesa napolitana', 890, 'Milanesa napolitana. Jamón, queso y tomate. Sin pasa de uva.');
INSERT INTO dish (restaurantId, dishName, price, dishDescription)
VALUES(1,'Pizza calabresa', 650, 'Pizza tipo italiana con MUCHA calabresa. Es un poquito picante.');
INSERT INTO dish (restaurantId, dishName, price, dishDescription)
VALUES(1,'Empanada de carne', 140, 'Empanada de carne cortada a cuchillo, sin aceitunas, sin pasas de uva y sin papa.');
INSERT INTO dish (restaurantId, dishName, price, dishDescription)
VALUES(1,'Flan con dulce de leche', 400, 'El mejor flan con dulce de leche de Buenos Aires.');
INSERT INTO dish (restaurantId, dishName, price, dishDescription)
VALUES(1,'Limonada', 350, 'Limonada bien fría con menta y gengibre, ideal para tomar si estás manejando.');
INSERT INTO dish (restaurantId, dishName, price, dishDescription)
VALUES(1,'Cerveza Amber Patagonia', 350, 'Una pinta de cerveza Patagonia Amber dulzona añejada en notas de nuez.');

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

INSERT INTO users (username, password, role)
VALUES('Pepito','123', 'ROLE_RESTAURANT');

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

