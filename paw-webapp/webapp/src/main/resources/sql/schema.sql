drop table if exists customer cascade;
drop table if exists restaurant cascade;
drop table if exists dish cascade;
drop table if exists reservation cascade;
drop table if exists orderItem cascade;

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
  FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId)
);

INSERT INTO dish (restaurantId, dishName, price)
VALUES(1,'Milanesa napolitana', 890);
INSERT INTO dish (restaurantId, dishName, price)
VALUES(1,'Pizza calabresa', 650);

CREATE TABLE IF NOT EXISTS reservation (
    reservationId   SERIAL PRIMARY KEY,
    restaurantId    integer NOT NULL,
    customerId      integer NOT NULL,
    reservationDate timestamp,
    FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId),
    FOREIGN KEY (customerId) REFERENCES customer (customerId)
);

INSERT INTO reservation (restaurantId, customerId, reservationDate)
VALUES(1, 1, NOW());

CREATE TABLE IF NOT EXISTS users (
  userId SERIAL PRIMARY KEY,
  username varchar(100),
  password varchar(100)
);

CREATE TABLE IF NOT EXISTS orderItem
(
    id          SERIAL PRIMARY KEY,
    dishId     integer NOT NULL,
    reservationId serial NOT NULL,
    unitPrice     decimal(12,2) NOT NULL,
    quantity      integer NOT NULL,
    status        integer NOT NULL,
    FOREIGN KEY ( reservationId ) REFERENCES reservation ( reservationId ),
    FOREIGN KEY ( dishId ) REFERENCES dish ( dishId )
);

INSERT INTO orderItem (dishId, reservationId, unitPrice, quantity, status)
VALUES(1, 1, 890, 3, 0);

INSERT INTO orderItem (dishId, reservationId, unitPrice, quantity, status)
VALUES(2, 1, 650, 3, 0);