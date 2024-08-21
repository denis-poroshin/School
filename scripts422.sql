CREATE TABLE car(
                    id INTEGER PRIMARY KEY NOT NULL ,
                    brand VARCHAR(30),
                    model VARCHAR(30),
                    price NUMERIC(16, 2)
);

CREATE TABLE human (
                       id INTEGER PRIMARY KEY NOT NULL,
                       name VARCHAR(30),
                       age INTEGER DEFAULT (18),
                       provo BOOLEAN NOT NULL ,
                       car_id INTEGER REFERENCES car (id) NOT NULL
);
INSERT INTO car (id, brand, model, price)
values (1, 'Нисан', 'Кашкай', 2000000.00);
SELECT * FROM car;
INSERT INTO human (id, name, age, provo, car_id)
values (1, 'Олег', 25, true, 1);

INSERT INTO human (id, name, age, provo, car_id)
values (2, 'Никита', 26, false, 1);

SELECT * FROM human;



SELECT human.id, human.name, human.age, human.provo, car.brand, car.model, car.price
FROM human
         INNER JOIN car on human.car_id = car.id;