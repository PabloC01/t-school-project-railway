package com.tsystems.pablo_canton.railway.setup.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SeatNotFreeException extends RuntimeException{

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
