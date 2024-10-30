package com.questoes.questoes.web.controller;

import com.questoes.questoes.service.disciplina.DisciplinaService;
import com.questoes.questoes.web.dto.ResponseMensagemDTO;
import com.questoes.questoes.web.dto.disciplina.DisciplinaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disciplina")
@RequiredArgsConstructor
public class DisciplinaController {

    private final DisciplinaService service;

    @GetMapping
    public ResponseEntity<List<DisciplinaDTO>> buscarTodasAsDisciplinas() {
        return ResponseEntity.ok().body(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity<ResponseMensagemDTO> cadastrarDisciplina(@RequestBody String nome) {
        service.salvarDisciplina(nome);
        ResponseMensagemDTO responseMensagemDTO = new ResponseMensagemDTO();
        responseMensagemDTO.setMessage("Disciplina salva com sucesso");
        return ResponseEntity.ok().body(responseMensagemDTO);
    }
}
