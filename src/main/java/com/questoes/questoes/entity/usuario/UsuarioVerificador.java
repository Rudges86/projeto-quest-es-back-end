package com.questoes.questoes.entity.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuarioVerificador")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioVerificador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid")
    private Integer uuid;
    @Column(name = "tempoExpiracao")
    private Instant tempoExpiracao;
    @Column(name = "senhaTemporaria")
    private String senhaTemporaria;
    @Column(name="tempoExpiracaoSenhaTemporaria")
    private Instant tempoExpiracaoSenhaTemporaria;
    @Column(name="contador")
    private Integer contador = 0;

    @ManyToOne
    @JoinColumn(name="ID_USUARIO", referencedColumnName = "ID", unique = true)
    private Usuarios usuario;
}
