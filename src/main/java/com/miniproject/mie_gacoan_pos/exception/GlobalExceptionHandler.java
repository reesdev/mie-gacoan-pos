package com.miniproject.mie_gacoan_pos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFound(ProductNotFoundException ex) {
        return new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleStock(InsufficientStockException ex) {
        return new ErrorResponse(
                "BAD_REQUEST",
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception ex) {
        return new ErrorResponse(
                "ERROR",
                ex.getMessage(),
                LocalDateTime.now()
        );
    }
}