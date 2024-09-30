package com.questoes.questoes.web.dto.usuario;

import com.questoes.questoes.entity.enumerate.role.Role;
import com.questoes.questoes.entity.enumerate.status.StatusUsuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioPerfilDTO {

    private String nome;
    private String email;
    private byte[] imagemPerfil;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private StatusUsuario status;


}
