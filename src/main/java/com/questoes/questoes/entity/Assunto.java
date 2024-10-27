package com.questoes.questoes.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assunto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "assunto")
    private String assunto;

    @ManyToOne
    @JoinColumn(name="disciplina_id", referencedColumnName = "id")
    @JsonBackReference
    private Disciplina disciplina;



}
