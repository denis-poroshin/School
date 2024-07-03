package ru.hogwarts.school.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TheStudentIsAlreadyStudyingAtHogwartsException extends RuntimeException{
    public TheStudentIsAlreadyStudyingAtHogwartsException(String message) {
        super(message);
    }
}
