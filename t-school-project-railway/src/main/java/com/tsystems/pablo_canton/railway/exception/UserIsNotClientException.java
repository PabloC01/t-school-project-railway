package com.tsystems.pablo_canton.railway.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserIsNotClientException extends RuntimeException{

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
