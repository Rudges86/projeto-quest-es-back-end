package com.questoes.questoes.repository.questoes;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.questoes.questoes.entity.Banca;
import com.questoes.questoes.entity.Disciplina;

import java.util.List;
@JsonPropertyOrder({"id","texto", "pergunta" ,"resposta", "alternativas","banca","disciplina", "imagem"})
public interface QuestoesProjection {
    Long getId();
    String getPergunta();
    List<String> getAlternativas();
    String getResposta();
    BancaProjection getBanca();
    DisciplinaProjection getDisciplina();
    String getTexto();
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
