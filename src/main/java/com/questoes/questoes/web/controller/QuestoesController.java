package com.questoes.questoes.web.controller;

import com.questoes.questoes.config.security.jwt.JwtUserDetails;
import com.questoes.questoes.repository.questoes.QuestoesProjection;
import com.questoes.questoes.service.Questoes.QuestoesService;
import com.questoes.questoes.web.dto.ResponseMensagemDTO;
import com.questoes.questoes.web.dto.questoes.CadastrarQuestoesDTO;
import com.questoes.questoes.web.dto.mapper.PageableMapper;
import com.questoes.questoes.web.dto.pageable.PageableDTO;
import com.questoes.questoes.web.dto.questoes.RespostaUsuarioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("api/questoes")
@RequiredArgsConstructor
public class QuestoesController {


    private final QuestoesService questoesService;

    /* caso queira cadastrar isolado
    @PostMapping("/cadastrarBanca")

    @PostMapping("/cadastrarDisciplina")
    */
    /*Já pode cadastrar por inteiro*/
    @PostMapping("/cadastrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseMensagemDTO> cadastrarQuestoes(@RequestParam(value = "imagem", required = false) MultipartFile imagem, @ModelAttribute CadastrarQuestoesDTO dto) {
        if(imagem.isEmpty() || imagem == null) {
            ResponseMensagemDTO responseMensagemDTO = questoesService.cadastrarQuestao(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMensagemDTO);
        }

        ResponseMensagemDTO responseMensagemDTO = questoesService.cadastrarQuestao(imagem, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMensagemDTO);
    }

    @GetMapping()
    public ResponseEntity<PageableDTO> buscarTodasAsQuestoes(
            @RequestParam(name="banca", required = false, defaultValue = "") Long bancaId,
            @RequestParam(name="disciplina", required = false, defaultValue = "") Long disciplinaId,
            @PageableDefault(size = 5, sort= {"pergunta"}) Pageable pageable) {
        Page<QuestoesProjection> questoes = questoesService.buscarTodasQuestoes(pageable);
        return ResponseEntity.ok().body(PageableMapper.toDto(questoes));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USUARIO')")
    public ResponseEntity<ResponseMensagemDTO> responderQuestao(@RequestParam Long id, @RequestBody RespostaUsuarioDto respostaUsuario, @AuthenticationPrincipal JwtUserDetails userDetails) {
        ResponseMensagemDTO mensagemDTO = questoesService.resposta(id,respostaUsuario, userDetails.getUsername());
        return ResponseEntity.ok().body(mensagemDTO);
    }
}
