package com.questoes.questoes.service.usuarioquestao;

import com.questoes.questoes.entity.Questoes;
import com.questoes.questoes.entity.UsuarioQuestao;
import com.questoes.questoes.entity.usuario.Usuarios;
import com.questoes.questoes.repository.usuarioquestao.UsuarioQuestaoRepository;
import com.questoes.questoes.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioQuestaoService {

    private final UsuarioQuestaoRepository usuarioQuestaoRepository;
    private final UsuarioService usuarioService;
    @Transactional
    public void fazerMarcacao(String resultado, String usuarios, Questoes questoes) {
        Usuarios usuario = usuarioService.buscarUsuarioEmail(usuarios);

        UsuarioQuestao usuarioQuestao = usuarioQuestaoRepository.findByUsuariosIdAndQuestoesId(usuario.getId(), questoes.getId());

        if(usuarioQuestao != null){
            usuarioQuestao.setUsuarios(usuario);
            usuarioQuestao.setQuestoes(questoes);
            usuarioQuestao.setAcertou(false);
            if(resultado.equals("Correta")) {
                usuarioQuestao.setAcertou(true);
            }
            usuarioQuestaoRepository.save(usuarioQuestao);
        } else {
            usuarioQuestao = new UsuarioQuestao();
            usuarioQuestao.setUsuarios(usuario);
            usuarioQuestao.setQuestoes(questoes);
            usuarioQuestao.setAcertou(false);
            if(resultado.equals("Correta")) {
                usuarioQuestao.setAcertou(true);
            }
            usuarioQuestaoRepository.save(usuarioQuestao);
        }



        //Implementar a busca pelo id e salvar por cima, caso ele já exista, caso não exista salvar um novo

    }

}
