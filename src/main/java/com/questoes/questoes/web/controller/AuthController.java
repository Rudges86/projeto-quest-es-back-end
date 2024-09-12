package com.questoes.questoes.web.controller;

import com.questoes.questoes.config.security.jwt.JwtDetailsService;
import com.questoes.questoes.config.security.jwt.JwtToken;
import com.questoes.questoes.web.dto.usuario.UsuarioLoginDTO;
import com.questoes.questoes.web.exception.exceptions.InvalidCredencialException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDTO dto) {

      //  usuarioRepository.autenticar(dto);
        log.info("Processo de autenticação pelo login {}", dto.getEmail());
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(dto.getEmail());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.warn("Credênciais inválidas '{}'", dto.getEmail());
            throw new InvalidCredencialException(dto.getEmail());
        }
    }

}
