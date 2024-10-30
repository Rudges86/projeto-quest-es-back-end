package com.questoes.questoes.service.banca;

import com.questoes.questoes.entity.Banca;
import com.questoes.questoes.repository.banca.BancaRepository;
import com.questoes.questoes.web.dto.ResponseMensagemDTO;
import com.questoes.questoes.web.dto.banca.BancaDto;
import com.questoes.questoes.web.dto.mapper.BancaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BancaService {

    private final BancaRepository repository;

    @Transactional
    public ResponseMensagemDTO salvarBanca(String nomeBanca) {
        ResponseMensagemDTO responseMensagemDTO = new ResponseMensagemDTO();
        Banca banca = new Banca();
        banca.setNome(nomeBanca);
        try {
            repository.save(banca);
            responseMensagemDTO.setMessage("Banca Salva com Sucesso!");
            return responseMensagemDTO;
        } catch (DataIntegrityViolationException ex) {
            responseMensagemDTO.setMessage(ex.getMessage());
            return responseMensagemDTO;
        }
    }

    @Transactional(readOnly = true)
    public Banca buscarBancaNome(String nomeBanca) {
        Banca banca = repository.findByNome(nomeBanca);
        return banca;
    }

    @Transactional(readOnly = true)
    public List<BancaDto> listarTodas() {
        return BancaMapper.toListDto(repository.findAll());
    }
}
