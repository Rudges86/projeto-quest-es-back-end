package com.questoes.questoes.repository.usuario;

import com.questoes.questoes.entity.enumerate.role.Role;
import com.questoes.questoes.entity.usuario.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    //@Query("select u from usuario u where u.email like :email")
    Optional<Usuarios> findByEmail(String email);

    @Query("select u.role from Usuarios u where u.email = :email")
    Role findByRole(String email);
}
