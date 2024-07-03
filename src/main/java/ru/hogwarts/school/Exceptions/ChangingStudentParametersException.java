package ru.hogwarts.school.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChangingStudentParametersException extends RuntimeException{
    public ChangingStudentParametersException(String message) {
        super(message);
    }
}
