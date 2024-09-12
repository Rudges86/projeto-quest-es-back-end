package com.questoes.questoes.service.Questoes;

import com.questoes.questoes.entity.Banca;
import com.questoes.questoes.entity.Disciplina;
import com.questoes.questoes.entity.Questoes;
import com.questoes.questoes.entity.UsuarioQuestao;
import com.questoes.questoes.entity.enumerate.dificuldade.Dificuldade;
import com.questoes.questoes.repository.questoes.QuestoesProjection;
import com.questoes.questoes.repository.questoes.QuestoesRepository;
import com.questoes.questoes.repository.usuarioquestao.UsuarioQuestaoRepository;
import com.questoes.questoes.service.banca.BancaService;
import com.questoes.questoes.service.disciplina.DisciplinaService;
import com.questoes.questoes.service.usuario.UsuarioService;
import com.questoes.questoes.service.usuarioquestao.UsuarioQuestaoService;
import com.questoes.questoes.web.dto.ResponseMensagemDTO;
import com.questoes.questoes.web.dto.disciplina.CadastrarQuestoesDTO;
import com.questoes.questoes.web.dto.questoes.RespostaUsuarioDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestoesService {

    private final QuestoesRepository questoesRepository;
    private final DisciplinaService disciplinaService;
    private final BancaService bancaService;
    private final UsuarioQuestaoService usuarioQuestaoService;

    @Transactional
    public ResponseMensagemDTO cadastrarQuestao(MultipartFile imagem, CadastrarQuestoesDTO cadastrarQuestao) {
        ResponseMensagemDTO mensagem =  new ResponseMensagemDTO();
        mensagem.setMessage("Salvo com sucesso!");

        try {
            Banca banca = bancaService.buscarBancaNome(cadastrarQuestao.getBanca());
            Disciplina disciplina = disciplinaService.buscarDisciplina(cadastrarQuestao.getDisciplina());
            if(banca == null) {
                bancaService.salvarBanca(cadastrarQuestao.getBanca());
                banca = bancaService.buscarBancaNome(cadastrarQuestao.getBanca());
            }

            if(disciplina == null) {
                disciplinaService.salvarDisciplina(cadastrarQuestao.getDisciplina());
                disciplina = disciplinaService.buscarDisciplina(cadastrarQuestao.getDisciplina());
            }


            Questoes questao = new Questoes();
            questao.setBanca(banca);
            questao.setDisciplina(disciplina);
            questao.setPergunta(cadastrarQuestao.getPergunta());
            questao.setAlternativas(cadastrarQuestao.getAlternativas());
            questao.setResposta(cadastrarQuestao.getResposta());
            questao.setImagem(imagem.getBytes());

            questoesRepository.save(questao);

            return mensagem;
        } catch (DataIntegrityViolationException ex) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public ResponseMensagemDTO cadastrarQuestao(CadastrarQuestoesDTO cadastrarQuestao) {
        ResponseMensagemDTO mensagem =  new ResponseMensagemDTO();
        mensagem.setMessage("Salvo com sucesso!");
        try {
            Banca banca = bancaService.buscarBancaNome(cadastrarQuestao.getBanca());
            Disciplina disciplina = disciplinaService.buscarDisciplina(cadastrarQuestao.getDisciplina());
            if(banca == null) {
                bancaService.salvarBanca(cadastrarQuestao.getBanca());
                banca = bancaService.buscarBancaNome(cadastrarQuestao.getBanca());
            }

            if(disciplina == null) {
                disciplinaService.salvarDisciplina(cadastrarQuestao.getDisciplina());
                disciplina = disciplinaService.buscarDisciplina(cadastrarQuestao.getDisciplina());
            }

            Questoes questao = new Questoes();
            questao.setBanca(banca);
            questao.setDisciplina(disciplina);
            questao.setPergunta(cadastrarQuestao.getPergunta());
            questao.setAlternativas(cadastrarQuestao.getAlternativas());
            questao.setResposta(cadastrarQuestao.getResposta());
            questao.setAno(cadastrarQuestao.getAno());
            questao.setDificuldade(Dificuldade.valueOf(cadastrarQuestao.getDificuldade().toUpperCase()));
            questoesRepository.save(questao);

            return mensagem;
        } catch (DataIntegrityViolationException ex) {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Page<QuestoesProjection> buscarTodasQuestoes(Pageable pageable) {
        return questoesRepository.findAllPageable(pageable);

    }

    @Transactional
    public ResponseMensagemDTO resposta(Long id, RespostaUsuarioDto respostaUsuarioDto, String email) {
        Optional<Questoes> questoes = questoesRepository.findById(id);
        ResponseMensagemDTO responseMensagemDTO = new ResponseMensagemDTO();
        if(questoes.get().getResposta().equals(respostaUsuarioDto.getResposta())) {
            usuarioQuestaoService.fazerMarcacao("Correta", email,questoes.get());
            responseMensagemDTO.setMessage("Correta");
        } else {
            usuarioQuestaoService.fazerMarcacao("Errou", email,questoes.get());
            responseMensagemDTO.setMessage("Errou");
        }

        return responseMensagemDTO;
    }

}
