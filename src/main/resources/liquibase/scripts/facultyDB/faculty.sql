-- liquibase formatted sql

-- changeset dporoshin:1

CREATE TABLE faculty(
                id INTEGER PRIMARY KEY,
                name VARCHAR(30),
                color VARCHAR(30)
);

