package com.questoes.questoes;

import com.questoes.questoes.service.Questoes.QuestoesService;
import com.questoes.questoes.web.dto.ResponseMensagemDTO;
import com.questoes.questoes.web.dto.banca.BancaDto;
import com.questoes.questoes.web.dto.disciplina.CadastrarQuestoesDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/questoes/questoes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/questoes/questoes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class QuestoesTest {
    @Autowired
    WebTestClient testClient;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private QuestoesService questoesService;

    @Test
    public void cadastrarBanca_RetornarStatus201() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile("imagem", "teste.jpg",
                "image/jpeg", new byte[100]);

        MultiValueMap<String, String> cadastrarQuestoesDTO = new LinkedMultiValueMap<>();
        cadastrarQuestoesDTO.add("titulo", "Teste");
        cadastrarQuestoesDTO.add("disciplina", "Português");
        cadastrarQuestoesDTO.add("pergunta", "Quantas letras tem a palavra 'teste'?");
        cadastrarQuestoesDTO.add("ano", "2024");
        cadastrarQuestoesDTO.add("alternativas[0]", "a) 2 letras");
        cadastrarQuestoesDTO.add("alternativas[1]", "b) 3 letras");
        cadastrarQuestoesDTO.add("alternativas[2]", "c) 4 letras");
        cadastrarQuestoesDTO.add("alternativas[3]", "d) 5 letras");
        cadastrarQuestoesDTO.add("resposta", "d) 5 letras");
        cadastrarQuestoesDTO.add("dificuldade", "fácil");
/*        CadastrarQuestoesDTO cadastrarQuestoesDTO = new CadastrarQuestoesDTO("Teste","Português","Quantas letras tem a palavra 'teste'?",
                "2024", Arrays.asList("a) 2 letras", "b) 3 letras", "c) 4 letras","d) 5 letras")
                ,"d) 5 letras","fácil"); */
        ResponseMensagemDTO responseBody = testClient
                .post()
                .uri("/api/questoes/cadastrar")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "rudges86@gmail.com", "123456"))
                .body(BodyInserters.fromMultipartData(cadastrarQuestoesDTO).with("imagem", mockFile.getResource()) )
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ResponseMensagemDTO.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getMessage()).isEqualTo("Salvo com sucesso!");

    }

}
