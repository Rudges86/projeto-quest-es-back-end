package com.questoes.questoes.repository.banca;

import com.questoes.questoes.entity.Banca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancaRepository extends JpaRepository<Banca, Long> {
    public Banca findByNome(String nome);
}
