package ru.itis.masternode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class TestCaseQueueLimitViolationException extends RuntimeException {

    public TestCaseQueueLimitViolationException(String message) {
        super(message);
    }

}
