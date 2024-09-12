package com.questoes.questoes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.questoes.questoes.entity.enumerate.dificuldade.Dificuldade;
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
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questoes")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Questoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Long id;

    @Column(nullable = false, name="pergunta")
    private String pergunta;

    @Column(name="imagem", length = 16777215)
    private byte[] imagem;

    @Column(name = "alternativas", nullable = false)
    @ElementCollection
    private List<String> alternativas;


    @Column(name="resposta", nullable = false)
    private String resposta;

    @Column(name="ano", nullable = false)
    private String ano;

    @Enumerated(EnumType.STRING)
    @Column(name="dificuldade", nullable = false)
    private Dificuldade dificuldade;

    @ManyToOne
    @JoinColumn(name="banca_id", referencedColumnName = "id")
    @JsonBackReference
    private Banca banca;

    @ManyToOne
    @JoinColumn(name="disciplina_id", referencedColumnName = "id")
    @JsonBackReference
    private Disciplina disciplina;


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
        return "Questoes{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questoes questoes = (Questoes) o;
        return Objects.equals(id, questoes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
