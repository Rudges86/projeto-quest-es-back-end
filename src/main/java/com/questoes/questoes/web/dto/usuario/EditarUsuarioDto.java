package com.questoes.questoes.web.dto.usuario;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EditarUsuarioDto {

    @NotEmpty
    private String nome;
    private String email;
}
