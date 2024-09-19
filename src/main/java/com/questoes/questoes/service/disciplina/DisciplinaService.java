package com.questoes.questoes.service.disciplina;

import com.questoes.questoes.entity.Disciplina;
import com.questoes.questoes.repository.disciplina.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DisciplinaService {
    private final DisciplinaRepository disciplinaRepository;

    @Transactional
    public void salvarDisciplina (String nomeDisciplina) {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(nomeDisciplina);
        try {
            disciplinaRepository.save(disciplina);
        } catch (DataIntegrityViolationException ex) {
            ex.getMessage();
        }
    }

    @Transactional(readOnly = true)
    public Disciplina buscarDisciplina(String nomeDisciplina) {
        Disciplina disciplina = disciplinaRepository.findByNome(nomeDisciplina);
        return disciplina;
    }

}