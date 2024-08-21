package ru.hogwarts.school.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.hogwarts.school.exception.AvatarProcessingException;
import ru.hogwarts.school.exception.NotCorrectValueException;
import ru.hogwarts.school.exception.NotFoundException;

@RestControllerAdvice
public class HogwartsExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
    @ExceptionHandler(AvatarProcessingException.class)
    public ResponseEntity<String> handleAvatarProcessingException(){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Не удалось прочитать аватарку из запроса или файла");
    }
    @ExceptionHandler(NotCorrectValueException.class)
    public ResponseEntity<String> handleNotCorrectValueException(){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Значение введено некорректно");
    }
}
