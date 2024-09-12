package com.questoes.questoes;

import com.questoes.questoes.config.security.jwt.JwtToken;
import com.questoes.questoes.web.dto.usuario.UsuarioLoginDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String email, String password) {
        String token = client
                .post()
                .uri("/api/login")
                .bodyValue(new UsuarioLoginDTO(email, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody().getToken();

        return headers -> headers.add(org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
