package ru.senya.directory.controller.exceptionHandler.exception;

public class RegionNotFoundException extends RuntimeException{
    public RegionNotFoundException(String message) {
        super(message);
    }
}
