package ru.itis.workernode.exception.model;

import org.springframework.http.HttpStatus;

public class PostDataException extends BaseException {
    public PostDataException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
