package com.questoes.questoes.web.controller;

import com.questoes.questoes.service.usuario.UsuarioService;
import com.questoes.questoes.web.dto.ResponseMensagemDTO;
import com.questoes.questoes.web.dto.mapper.UsuarioMapper;
import com.questoes.questoes.web.dto.usuario.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("api")
@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseMensagemDTO> cadastro(@RequestBody @Valid CadastrarUsuarioDTO usuarios){
        ResponseMensagemDTO responseDTO = new ResponseMensagemDTO();
        responseDTO.setMessage(usuarioService.createUsuario(usuarios));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/recuperarUsuario")
    public ResponseEntity<ResponseMensagemDTO> recuperarUsuario(@RequestBody @Valid RecuperarUsuarioDTO dto) {
        ResponseMensagemDTO responseMensagemDTO = new ResponseMensagemDTO();
        responseMensagemDTO.setMessage(usuarioService.recuperarUsuario(dto.getEmail()));
        return ResponseEntity.ok().body(responseMensagemDTO);
    }

    @GetMapping("/validar/{uuid}")
    public ResponseEntity<ResponseMensagemDTO> validado(@PathVariable String uuid) {
        Integer uid = Integer.parseInt(uuid);
        ResponseMensagemDTO responseMensagemDTO = new ResponseMensagemDTO();
        responseMensagemDTO.setMessage(usuarioService.validarUsuario(uid));
        return ResponseEntity.ok().body(responseMensagemDTO);
    }

    @GetMapping("/alterarSenhaObrigatorio/{id}")
    public ResponseEntity<ResponseMensagemDTO> alteraSenhaObrigatorio(@PathVariable String id ) {
        Integer usuario = Integer.parseInt(id);
        ResponseMensagemDTO mensagemDTO = new ResponseMensagemDTO();
        mensagemDTO.setMessage("Link de acesso inválido, por favor solicite um novo.");
        if(usuarioService.validaAlteracaoSenha(usuario)){
            mensagemDTO.setMessage("Link de acesso válido, altere a senha.");
            return ResponseEntity.ok().body(mensagemDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemDTO);
    }

    @PatchMapping("/alterarSenhaObrigatorio/{id}")
    public ResponseEntity<ResponseMensagemDTO> alteraSenhaObrigatorio(@PathVariable String id, @RequestBody @Valid AlterarUsuarioObrigatorioDTO alterarUsuarioDTO) {
        Integer usuario = Integer.parseInt(id);

        return ResponseEntity.ok().body(usuarioService.alterarSenhaObrigatorio(Integer.parseInt(id),alterarUsuarioDTO));
    }

    @PatchMapping("/editarPerfil")
    public ResponseEntity<ResponseMensagemDTO> editarPerfil(@RequestParam(value = "imagem", required = false) MultipartFile imagem,
                                          @ModelAttribute EditarUsuarioDto dto) {
            ResponseMensagemDTO responseMensagemDTO = usuarioService.editarUsuario(imagem, dto);
            return ResponseEntity.ok().body(responseMensagemDTO);
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioPerfilDTO> recuperarDadosDoPerfil() {
        return ResponseEntity.ok().body(usuarioService.recuperarPerfilUsuario());
    }
  /*  public ResponseEntity<Resource> recuperarDadosDoPerfil(@PathVariable String id) {
       byte[] imagem =  usuarioService.recuperarPerfil(id);
        ByteArrayResource resource = new ByteArrayResource(imagem);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
       return ResponseEntity.ok().headers(headers).contentLength(imagem.length).body(resource);
    }*/
}
