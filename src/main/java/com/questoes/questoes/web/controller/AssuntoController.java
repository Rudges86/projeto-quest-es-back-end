package com.questoes.questoes.web.controller;

import com.questoes.questoes.service.assunto.AssuntoService;
import com.questoes.questoes.web.dto.ResponseMensagemDTO;
import com.questoes.questoes.web.dto.assunto.AssuntoDTO;
import com.questoes.questoes.web.dto.disciplina.DisciplinaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assunto")
@RequiredArgsConstructor
public class AssuntoController {
    private final AssuntoService service;

    @GetMapping("/{disciplinaId}")
    public ResponseEntity<List<AssuntoDTO>> listarTodosAssuntos(@PathVariable Long disciplinaId) {
        return ResponseEntity.ok().body(service.listarTodos(disciplinaId));
    }

    @PostMapping
    public ResponseEntity<ResponseMensagemDTO> cadastrarAssunto(@RequestBody AssuntoDTO assuntoDTO, @RequestBody DisciplinaDTO disciplinaDTO) {
        return ResponseEntity.ok().body(service.salvarAssunto(assuntoDTO.getAssunto(), disciplinaDTO.getNome()));
    }

    //Fazer um editar
}
