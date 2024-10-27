package com.questoes.questoes.repository.usuario;

import com.questoes.questoes.entity.enumerate.role.Role;
import com.questoes.questoes.entity.enumerate.status.StatusUsuario;
import com.questoes.questoes.entity.usuario.UsuarioVerificador;
import com.questoes.questoes.entity.usuario.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    //@Query("select u from usuario u where u.email like :email")
    Optional<Usuarios> findByEmail(String email);

    @Query("select u.role from Usuarios u where u.email = :email")
    Role findByRole(String email);

    @Query("SELECT u FROM Usuarios u WHERE u.status = :status AND u.dataCriacao < :limite")
    List<Usuarios> findByStatus(@Param("status") StatusUsuario status, @Param("limite") LocalDateTime limite);
}
