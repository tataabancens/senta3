CREATE TABLE IF NOT EXISTS customer (
  customerId SERIAL PRIMARY KEY,
  customerName varchar(50) NOT NULL,
  Phone varchar(50) NOT NULL,
  Mail varchar(50)
);

ALTER TABLE customer ADD COLUMN IF NOT EXISTS userId int;
ALTER TABLE customer ADD COLUMN IF NOT EXISTS points int default 0;
-- ALTER TABLE customer
--     ADD CONSTRAINT IF NOT EXISTS fk_customer_users FOREIGN KEY (userId) REFERENCES users (userId);

CREATE TABLE IF NOT EXISTS restaurant (
  restaurantId SERIAL PRIMARY KEY,
  restaurantName varchar(100) NOT NULL,
  phone varchar(100) NOT NULL,
  Mail varchar(50) NOT NULL,
  totalChairs int NOT NULL
);

ALTER TABLE restaurant ADD COLUMN IF NOT EXISTS totalChairs int DEFAULT 10 NOT NULL;
ALTER TABLE restaurant DROP COLUMN IF EXISTS totalTables;
ALTER TABLE restaurant ADD COLUMN IF NOT EXISTS openHour int DEFAULT '0';
ALTER TABLE restaurant ADD COLUMN IF NOT EXISTS closeHour int DEFAULT '0';
ALTER TABLE restaurant ADD COLUMN IF NOT EXISTS userId int DEFAULT 1;
-- ALTER TABLE restaurant
--     ADD CONSTRAINT IF NOT EXISTS fk_restaurant_users FOREIGN KEY (userId) REFERENCES users (userId);


CREATE TABLE IF NOT EXISTS image
(
    imageId serial PRIMARY KEY,
    bitmap bytea
);


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

ALTER TABLE dish ADD IF NOT EXISTS imageId integer default 1 NOT NULL;
ALTER TABLE dish DROP IF EXISTS category;

-- ALTER TABLE dish ADD IF NOT EXISTS category_id int default 1 not null
-- Constraint fk_category_id references dishcategory on update cascade;


CREATE TABLE IF NOT EXISTS reservation (
    reservationId   SERIAL PRIMARY KEY,
    restaurantId    integer NOT NULL,
    customerId      integer NOT NULL,
    reservationHour integer NOT NULL,
    reservationStatus integer,
    qPeople integer default 1,
    FOREIGN KEY (restaurantId) REFERENCES restaurant (restaurantId),
    FOREIGN KEY (customerId) REFERENCES customer (customerId)
);

-- ALTER TABLE reservation DROP COLUMN IF EXISTS reservationDate;

ALTER TABLE reservation ADD IF NOT EXISTS reservationHour integer default 0 NOT NULL;

ALTER TABLE reservation ADD IF NOT EXISTS reservationDiscount boolean default false NOT NULL;

ALTER TABLE reservation ADD IF NOT EXISTS qPeople integer default 1;

ALTER TABLE reservation ADD IF NOT EXISTS startedAtTime timestamp default now();

ALTER TABLE reservation ADD IF NOT EXISTS reservationDate timestamp;

ALTER TABLE reservation ADD IF NOT EXISTS tableNumber integer default 0;

ALTER TABLE reservation ADD COLUMN IF NOT EXISTS hand boolean default false NOT NULL;

ALTER TABLE reservation ADD COLUMN IF NOT EXISTS isToday boolean default false NOT NULL;





CREATE TABLE IF NOT EXISTS users (
  userId SERIAL PRIMARY KEY,
  username varchar(100) UNIQUE,
  password varchar(100),
  role varchar(100)
);

ALTER TABLE users ADD IF NOT EXISTS role varchar(100) default 'ROLE_RESTAURANT' NOT NULL;

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



-- INSERT INTO users(username, password, role)
-- values('Juancho Capo', '12345678', 'ROLE_CUSTOMER');


