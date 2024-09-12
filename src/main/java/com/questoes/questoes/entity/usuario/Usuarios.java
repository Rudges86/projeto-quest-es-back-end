package com.questoes.questoes.entity.usuario;

import com.questoes.questoes.entity.enumerate.role.Role;
import com.questoes.questoes.entity.enumerate.status.StatusUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="usuarios")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Long id;
    @Column(name = "nome", length = 100)
    private String nome;
    @Lob
    @Column(name="imagemPerfil",length = 16777215)
    private byte[] imagemPerfil;
    @Column(name="email", nullable = false, unique = true, length = 200)
    private String email;
    @Column(name="password", nullable = false, length = 100)
    private String password;

    @Column(name="role", nullable = false, length = 25)
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USUARIO;

    @Column(name="status", nullable = false, length = 36)
    @Enumerated(EnumType.STRING)
    private StatusUsuario status = StatusUsuario.PENDENTE;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;
    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;

    @Override
    public String toString() {
        return "Usuarios{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuarios usuarios = (Usuarios) o;
        return Objects.equals(id, usuarios.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
