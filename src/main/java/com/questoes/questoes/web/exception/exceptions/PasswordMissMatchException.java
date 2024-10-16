package com.questoes.questoes.web.exception.exceptions;

import lombok.Getter;

@Getter
public class PasswordMissMatchException extends RuntimeException{
    private String message;
    public PasswordMissMatchException(String message) {
        this.message = message;
    }
}
