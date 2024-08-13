-- liquibase formatted sql

-- changeset dporoshin:1

CREATE TABLE faculty(
                id INTEGER PRIMARY KEY,
                name VARCHAR(30),
                color VARCHAR(30)
);

CREATE INDEX faculty_name_and_color_index ON faculty (name, color);

