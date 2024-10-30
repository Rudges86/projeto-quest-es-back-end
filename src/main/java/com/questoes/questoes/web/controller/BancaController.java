package com.questoes.questoes.web.controller;

import com.questoes.questoes.service.banca.BancaService;
import com.questoes.questoes.web.dto.ResponseMensagemDTO;
import com.questoes.questoes.web.dto.banca.BancaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banca")
@RequiredArgsConstructor
public class BancaController {
    private final BancaService service;

    @GetMapping
    public ResponseEntity<List<BancaDto>> listarTodasAsBancas() {
        return ResponseEntity.ok().body(service.listarTodas());
    }

    @PostMapping
    public ResponseEntity<ResponseMensagemDTO> cadastrarBanca(@RequestBody String banca) {
        return ResponseEntity.ok().body(service.salvarBanca(banca));
    }
}
