package com.questoes.questoes.web.dto.disciplina;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CadastrarQuestoesDTO {
    private String banca;
    private String disciplina;
    private String pergunta;
    private String ano;
    private List<String> alternativas;
    private String resposta;
    private String dificuldade;
}
