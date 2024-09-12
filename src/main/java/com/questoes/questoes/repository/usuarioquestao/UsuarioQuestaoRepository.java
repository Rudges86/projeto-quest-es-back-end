package com.questoes.questoes.repository.usuarioquestao;

import com.questoes.questoes.entity.UsuarioQuestao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioQuestaoRepository extends JpaRepository<UsuarioQuestao, Long> {

    UsuarioQuestao findByUsuariosIdAndQuestoesId(Long usuarioId, Long questaoId);
}
