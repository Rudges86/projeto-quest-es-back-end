package com.questoes.questoes.service.banca;

import com.questoes.questoes.entity.Banca;
import com.questoes.questoes.repository.banca.BancaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BancaService {

    private final BancaRepository repository;

    @Transactional
    public void salvarBanca(String nomeBanca) {
        Banca banca = new Banca();
        banca.setNome(nomeBanca);

        try {
            repository.save(banca);
        } catch (DataIntegrityViolationException ex) {
            ex.getMessage();
        }

    }

    @Transactional(readOnly = true)
    public Banca buscarBancaNome(String nomeBanca) {
        Banca banca = repository.findByNome(nomeBanca);
        return banca;
    }


}
