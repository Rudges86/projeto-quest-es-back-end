package com.questoes.questoes.web.exception.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UUIDNotFoundException extends RuntimeException{
    private String codigo;
    private String recurso;

    public UUIDNotFoundException(String message) {
        super(message);
    }

    public UUIDNotFoundException(String codigo, String recurso) {
        this.codigo = codigo;
        this.recurso = recurso;
    }
}
