package com.questoes.questoes.repository.usuario;

import com.questoes.questoes.entity.usuario.UsuarioVerificador;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsuarioVerificadorRepository extends JpaRepository<UsuarioVerificador, Long> {

    Optional<UsuarioVerificador> findByUuid(Integer uuid);

    Optional<UsuarioVerificador> findByUsuarioId(Long id_usuario);
    Optional<UsuarioVerificador> findBySenhaTemporaria(String senhaTemporaria);

    //List<UsuarioVerificador> findByTimeExpiracao(LocalDateTime now);
}
