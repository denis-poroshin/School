-- liquibase formatted sql

-- changeset dporoshin:1

CREATE TABLE student (
    id INTEGER PRIMARY KEY,
    name VARCHAR(30),
    age INTEGER,
    faculty_id INTEGER REFERENCES faculty (id)
);

CREATE INDEX student_name_index ON student (name);


