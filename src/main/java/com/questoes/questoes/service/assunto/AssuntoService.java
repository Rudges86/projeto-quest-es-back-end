package com.questoes.questoes.service.assunto;

import com.questoes.questoes.entity.Assunto;
import com.questoes.questoes.entity.Disciplina;
import com.questoes.questoes.repository.assunto.AssuntoRepository;
import com.questoes.questoes.repository.disciplina.DisciplinaRepository;
import com.questoes.questoes.web.dto.ResponseMensagemDTO;
import com.questoes.questoes.web.dto.assunto.AssuntoDTO;
import com.questoes.questoes.web.dto.mapper.AssuntoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssuntoService {
    @Autowired
    private AssuntoRepository repository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    public Assunto buscarAssunto(String assunto) {
        return  repository.findByAssunto(assunto);

    }

    public ResponseMensagemDTO salvarAssunto(String assunto, String disciplinaNome) {
        ResponseMensagemDTO responseMensagemDTO = new ResponseMensagemDTO();
        try {
            Disciplina disciplina = disciplinaRepository.findByNome(disciplinaNome);
            Assunto novoAssunto = new Assunto();
            novoAssunto.setAssunto(assunto);
            novoAssunto.setDisciplina(disciplina);
            repository.save(novoAssunto);

            responseMensagemDTO.setMessage("Assunto Salvo com Sucesso!");
            return responseMensagemDTO;
        } catch (DataIntegrityViolationException ex) {
            ex.getMessage();
            return null;
        }

    }
    @Transactional(readOnly = true)
    public List<AssuntoDTO> listarTodos(Long disciplinaId) {
        return AssuntoMapper.toListDto(repository.findByDisciplinaId(disciplinaId));
    }
}
