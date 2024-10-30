package com.questoes.questoes.repository.assunto;

import com.questoes.questoes.entity.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssuntoRepository extends JpaRepository<Assunto,Long> {
    public Assunto findByAssunto(String assunto);

    List<Assunto> findByDisciplinaId(Long disciplinaId);
}
