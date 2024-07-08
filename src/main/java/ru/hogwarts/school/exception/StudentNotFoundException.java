package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends NotFoundException{
    public StudentNotFoundException(long id) {
        super(id);
    }
    @Override
    public String getMessage() {
        return "Студент с id = %d не найден".formatted(getId());
    }
}
