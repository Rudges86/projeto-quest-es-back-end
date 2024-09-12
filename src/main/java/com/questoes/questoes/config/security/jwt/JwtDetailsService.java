package com.questoes.questoes.config.security.jwt;

import com.questoes.questoes.entity.enumerate.role.Role;
import com.questoes.questoes.entity.usuario.Usuarios;

import com.questoes.questoes.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuario = usuarioService.buscarUsuarioEmail(username);
        return new JwtUserDetails(usuario);
    }

    public JwtToken getTokenAuthenticated(String username) {
        Role role = usuarioService.buscarUsuarioRole(username);
        return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
    }
}
