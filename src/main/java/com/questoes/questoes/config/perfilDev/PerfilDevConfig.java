package com.questoes.questoes.config.perfilDev;

import com.questoes.questoes.entity.enumerate.role.Role;
import com.questoes.questoes.entity.enumerate.status.StatusUsuario;
import com.questoes.questoes.entity.usuario.Usuarios;
import com.questoes.questoes.repository.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class PerfilDevConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;



    @Override
    public void run(String... args) throws Exception {
        Usuarios usuario = new Usuarios(null,"Rudge","rudge123@email.com","1234", Role.ROLE_ADMIN, StatusUsuario.ATIVO);
        usuarioRepository.save(usuario);
    }
}
