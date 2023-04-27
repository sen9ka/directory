package ru.senya.directory.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.senya.directory.controller.exceptionHandler.exception.RegionNotFoundException;

@ControllerAdvice
public class RegionExceptionHandler {

    @ExceptionHandler(RegionNotFoundException.class)
    public ResponseEntity<Object> handleRegionNotFoundException(RegionNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
