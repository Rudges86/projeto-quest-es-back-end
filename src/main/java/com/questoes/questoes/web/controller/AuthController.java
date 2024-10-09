package com.questoes.questoes.web.controller;

import com.questoes.questoes.config.security.jwt.JwtDetailsService;
import com.questoes.questoes.config.security.jwt.JwtToken;
import com.questoes.questoes.web.dto.usuario.UsuarioLoginDTO;
import com.questoes.questoes.web.exception.exceptions.InvalidCredencialException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
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

    @GetMapping("/valida-token")
    public ResponseEntity validaToken(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (detailsService.isTokenValid(token)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
