package com.questoes.questoes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.questoes.questoes.entity.usuario.Usuarios;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarioQuestao")
@Getter
@Setter
@NoArgsConstructor
public class UsuarioQuestao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Long id;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "usuarios_id", referencedColumnName = "id")
    private Usuarios usuarios;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "questoes_id", referencedColumnName = "id")
    private Questoes questoes;

    @Column(name = "acerto")
    private boolean acertou;

}
