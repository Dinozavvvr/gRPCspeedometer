package ru.itis.workernode.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import ru.itis.workernode.exception.model.BaseException;
import ru.itis.workernode.exception.model.ErrorMessage;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public Mono<ResponseEntity<ErrorMessage>> baseExceptionHandle(BaseException ex) {
        return Mono.just(ResponseEntity
                .status(ex.getStatusCode())
                .body(
                        ErrorMessage.builder()
                                .statusCode(ex.getStatusCode())
                                .message(ex.getMessage())
                                .build()
                )
        );
    }
}
