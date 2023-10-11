package com.tsystems.pablo_canton.railway.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAlreadyHaveTicketException extends RuntimeException{

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
