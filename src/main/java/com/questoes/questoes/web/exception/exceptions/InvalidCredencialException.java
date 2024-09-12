package com.questoes.questoes.web.exception.exceptions;

import lombok.Getter;

@Getter
public class InvalidCredencialException extends RuntimeException{

    private String nome;

    public InvalidCredencialException(String nome) {
        this.nome = nome;
    }
}
