package com.questoes.questoes.web.dto.usuario;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlterarUsuarioObrigatorioDTO {
    @NotNull
    private String senhaTemporaria;
    @NotNull
    private String novaSenha;
    @NotNull
    private String confirmeNovaSenha;

}
