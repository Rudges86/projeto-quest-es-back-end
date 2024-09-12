package com.questoes.questoes.config.security.jwt;

import com.questoes.questoes.entity.usuario.Usuarios;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {
    private Usuarios usuario;

    public JwtUserDetails(Usuarios usuario) {
        super(usuario.getEmail(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
        this.usuario = usuario;
    }

    public Long getId() {return this.usuario.getId();}
    public String getRole(){return this.usuario.getRole().name();}

}
