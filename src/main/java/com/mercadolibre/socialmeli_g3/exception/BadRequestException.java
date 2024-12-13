package com.mercadolibre.socialmeli_g3.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
