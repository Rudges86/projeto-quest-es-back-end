package com.questoes.questoes.web.exception.exceptions;

public class RegisteredUserException extends RuntimeException{
    private String message;
    private String codigo;

    public RegisteredUserException(String message, String codigo) {
        this.message = message;
        this.codigo = codigo;
    }

    public RegisteredUserException(String message) {
        super(message);
    }
}
