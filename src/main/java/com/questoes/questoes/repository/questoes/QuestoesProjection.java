package com.questoes.questoes.repository.questoes;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.questoes.questoes.entity.Banca;
import com.questoes.questoes.entity.Disciplina;

import java.util.List;
@JsonPropertyOrder({"id", "pergunta" ,"resposta", "alternativas","banca","disciplina", "imagem"})
public interface QuestoesProjection {
    Long getId();
    String getpergunta();
    List<String> getAlternativas();
    String getResposta();
    BancaProjection getBanca();
    DisciplinaProjection getDisciplina();
    byte[] getImagem();



    public interface BancaProjection {
        Long getId();
        String getNome();
    }
    public interface DisciplinaProjection {
        Long getId();
        String getNome();
    }

}
