package com.questoes.questoes.repository.disciplina;


import com.questoes.questoes.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina,Long> {
    public Disciplina findByNome(String nome);
}
