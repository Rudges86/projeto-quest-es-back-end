package com.questoes.questoes.web.dto.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEditarUsuarioDto {
    private String nome;
    private byte[] imagem;
    private String email;
}
