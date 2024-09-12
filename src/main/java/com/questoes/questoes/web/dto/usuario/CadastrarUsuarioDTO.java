package com.questoes.questoes.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastrarUsuarioDTO {
    @NotNull
    private String nome;

    @Email
    @NotNull
    private String email;
    @NotNull
    @Size(min=6, max=6)
    private String password;
    @NotNull
    @Size(min=6, max=6)
    private String confirmaSenha;
}
