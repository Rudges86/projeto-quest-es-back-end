package com.questoes.questoes.service.usuario;

import com.questoes.questoes.entity.enumerate.role.Role;
import com.questoes.questoes.entity.enumerate.status.StatusUsuario;
import com.questoes.questoes.entity.usuario.UsuarioVerificador;
import com.questoes.questoes.entity.usuario.Usuarios;
import com.questoes.questoes.repository.usuario.UsuarioRepository;
import com.questoes.questoes.repository.usuario.UsuarioVerificadorRepository;

import com.questoes.questoes.service.email.EmailService;
import com.questoes.questoes.web.dto.ResponseMensagemDTO;
import com.questoes.questoes.web.dto.mapper.UsuarioMapper;
import com.questoes.questoes.web.dto.usuario.AlterarUsuarioObrigatorioDTO;
import com.questoes.questoes.web.dto.usuario.CadastrarUsuarioDTO;
import com.questoes.questoes.web.dto.usuario.EditarUsuarioDto;
import com.questoes.questoes.web.dto.usuario.ResponseEditarUsuarioDto;
import com.questoes.questoes.web.exception.exceptions.EntityNotFoundException;
import com.questoes.questoes.web.exception.exceptions.RegisteredUserException;
import com.questoes.questoes.web.exception.exceptions.UUIDNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class UsuarioService {
    private final EmailService emailService;
    private final UsuarioRepository usuarioRepository;

    private final UsuarioVerificadorRepository usuarioVerificadorRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuarios buscarUsuarioCadastrado(String email) {
        Optional<Usuarios> usuarios = usuarioRepository.findByEmail(email);
        if(usuarios.isPresent()){
            throw new RegisteredUserException("Usuario já cadastrado");
        }
        return null;
    }
    @Transactional
    public String createUsuario(CadastrarUsuarioDTO usuario) {
            if(!usuario.getPassword().equals(usuario.getConfirmaSenha())) {
                return "As senhas são diferentes";
            }
            Usuarios usuarios = buscarUsuarioCadastrado(usuario.getEmail());

            usuarios = UsuarioMapper.toUsuario(usuario);
            usuarios.setPassword(passwordEncoder.encode(usuarios.getPassword()));
            usuarioRepository.save(usuarios);
            Integer uid = Integer.parseInt(gerarCodigoAleatorio());
            UsuarioVerificador usuarioVerificador = new UsuarioVerificador();
            usuarioVerificador.setUsuario(usuarios);
            usuarioVerificador.setUuid(uid);
            usuarioVerificador.setTempoExpiracao(Instant.now().plusSeconds(60));
            //http://localhost:8080/api/validar/%s
            String message = String.format("Olá %s, você foi cadastrado com sucesso!!, acesse o link:http://localhost:4200/validar/%s , " +
                               "para validar o usuário."
                      , usuarios.getEmail(), usuarioVerificador.getUuid());
            emailService.enviarEmailTexto(usuarios.getEmail(), "Cadastrado com sucesso", message);

            usuarioVerificadorRepository.save(usuarioVerificador);

            return "Usuario cadastrado com sucesso, siga as instruções no e-mail para fazer login.";
    }

    @Transactional
    public String recuperarUsuario(String email) {
       String mensagem = "";

       Usuarios usuarios =  usuarioRepository.findByEmail(email).orElseThrow(
               ()-> new EntityNotFoundException("Usuário não cadastrado"));

       String senhaTemporaria =  gerarCodigoAleatorio();
       Optional<UsuarioVerificador> usuarioVerificador = usuarioVerificadorRepository.findByUsuarioId(usuarios.getId());

       usuarioVerificador = populaUsuarioVerificador(senhaTemporaria,usuarios,usuarioVerificador);

       if(usuarioVerificador.get().getContador() >= 3 &&
            validaTempoExpiracao(usuarioVerificador.get().getTempoExpiracaoSenhaTemporaria())) {
            return "Tente novamente depois de 15 minutos.";
        }

       if(usuarioVerificador.get().getTempoExpiracaoSenhaTemporaria() != null &&
            !validaTempoExpiracao(usuarioVerificador.get().getTempoExpiracaoSenhaTemporaria())){
            usuarioVerificador.get().setContador(0);
        }



        usuarioVerificadorRepository.save(usuarioVerificador.get());

        mensagem = String.format("Ola %s a sua senha temporaria é esta %s , ela é válida por 15 minutos." +
                        " Clique no link para trocar a senha." +
                        " link:http://localhost:8080/api/alterarSenhaObrigatorio/%s"
                ,usuarios.getEmail(),senhaTemporaria,usuarioVerificador.get().getUuid());
        emailService.enviarEmailTexto(usuarios.getEmail(), "Recuperação de Senha e confirmação de e-mail", mensagem);
        return "Foi enviado um e-mail de confirmação por favor confira a sua caixa de entrada.";
    }

    @Transactional
    public String validarUsuario(Integer uuid) {
        UsuarioVerificador usuarioVerificador = usuarioVerificadorRepository.findByUuid(uuid)
                    .orElseThrow(()-> new UUIDNotFoundException("Link de ativação inválido."));
            Boolean tempoExpiracao = validaTempoExpiracao(usuarioVerificador.getTempoExpiracao());

            if (tempoExpiracao) {
                usuarioRepository.findById(usuarioVerificador.getUsuario().getId()).map(usuario -> {
                    usuario.setStatus(StatusUsuario.ATIVO);
                    usuarioRepository.save(usuario);
                    usuarioVerificadorRepository.deleteById(usuarioVerificador.getId());
                    return usuario;
                }).orElseThrow(() -> new RuntimeException("Não foi possível validar o usuário"));
                return "Usuário validado com sucesso!!";
            }
            usuarioVerificadorRepository.deleteById(usuarioVerificador.getId());
            return "Tempo de validação expirado, faça um novo cadastro";
    }


/*Criar um método get para validar os dados enviados no e-mail para que ele possa prosseguir para alterar a senha
 */



    @Transactional
    public ResponseMensagemDTO alterarSenhaObrigatorio(Integer id, AlterarUsuarioObrigatorioDTO dto) {
        UsuarioVerificador usuarioVerificador = usuarioVerificadorRepository.findByUuid(id)
                .orElseThrow(() -> new UUIDNotFoundException("Senha temporária inválida"));

        Optional<Usuarios> usuarios = usuarioRepository.findById(usuarioVerificador.getUsuario().getId());

        boolean validador = validaTempoExpiracao(usuarioVerificador.getTempoExpiracaoSenhaTemporaria());

        ResponseMensagemDTO mensagemDTO = new ResponseMensagemDTO();
        mensagemDTO.setMessage("Link expirado, solicite um novo.");

        if(validador) {
           if(dto.getNovaSenha().equals(dto.getConfirmeNovaSenha())
                    && dto.getSenhaTemporaria().equals(usuarioVerificador.getSenhaTemporaria())) {

                usuarios.get().setPassword(passwordEncoder.encode(dto.getNovaSenha()));
                usuarioRepository.save(usuarios.get());
                usuarioVerificadorRepository.deleteById(usuarioVerificador.getId());
                mensagemDTO.setMessage("Senha alterada com sucesso");
                return mensagemDTO;
            } else {
                mensagemDTO.setMessage("As senhas não conferem");
                return mensagemDTO;
            }

        }

        return mensagemDTO;
    }



    public boolean validaAlteracaoSenha(Integer id) {
        UsuarioVerificador usuarioVerificador = usuarioVerificadorRepository.findByUuid(id)
                .orElseThrow(() -> new UUIDNotFoundException("Código inválido ou expirado."));

        if(usuarioVerificador.getUsuario().getStatus() == StatusUsuario.PENDENTE) {
            usuarioRepository.findById(usuarioVerificador.getUsuario().getId()).map(user -> {
                user.setStatus(StatusUsuario.ATIVO);
                usuarioRepository.save(user);
                return user;
            });
        }
      return  validaTempoExpiracao(usuarioVerificador.getTempoExpiracaoSenhaTemporaria());
    }

    @Transactional(readOnly = true)
    public Usuarios buscarUsuarioEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Usuario", email)
        );
    }

    @Transactional(readOnly = true)
    public Role buscarUsuarioRole(String email) {
        return usuarioRepository.findByRole(email);
    }

    @Transactional
    public ResponseMensagemDTO editarUsuario(Long id, MultipartFile file, EditarUsuarioDto dto) {
        ResponseMensagemDTO responseMensagemDTO = new ResponseMensagemDTO();
        try {

            responseMensagemDTO.setMessage("Editado com Sucesso!");

            Usuarios usuarios = usuarioRepository.findById(id).orElseThrow(
                    ()-> new EntityNotFoundException("Não foi encontrado!!"));

            if(!usuarios.getNome().equals(dto.getNome())) {
                usuarios.setNome(dto.getNome());
            }

            if(file != null) {
                usuarios.setImagemPerfil(file.getBytes());
            }

            usuarioRepository.save(usuarios);

            return responseMensagemDTO;

        } catch (IOException ex) {
            responseMensagemDTO.setMessage("Problema ao salvar a imagem");
            return responseMensagemDTO;
        }
    }

    private boolean validaTempoExpiracao(Instant tempo) {
        Boolean tempoExpiracao = Duration.between(Instant.now(), tempo)
                .compareTo(Duration.ofSeconds(0)) >= 0;
        return tempoExpiracao;
    }

    //Para rotina de deletar
    private void atualizaUsuario(Boolean validador, Usuarios usuarios, UsuarioVerificador usuarioVerificador) {
        if(!validador) {
            usuarioRepository.deleteById(usuarios.getId());
            usuarioVerificadorRepository.deleteById(usuarioVerificador.getId());
        }
    }

    private String gerarCodigoAleatorio() {
        SecureRandom secureRandom = new SecureRandom();
        return String.valueOf(secureRandom.nextInt(10000));
    }


    private Optional<UsuarioVerificador> populaUsuarioVerificador(String senhaTemporaria, Usuarios usuarios,
                                                        Optional<UsuarioVerificador> usuarioVerificador) {
        if(usuarioVerificador.isEmpty()) {
            usuarioVerificador = Optional.of(new UsuarioVerificador());
        }

        usuarioVerificador.get().setUsuario(usuarios);
        usuarioVerificador.get().setUuid(Integer.parseInt(gerarCodigoAleatorio()));
        usuarioVerificador.get().setTempoExpiracao(Instant.now().plusSeconds( 15 * 60).atZone(ZoneId.systemDefault()).toInstant());
        usuarioVerificador.get().setTempoExpiracaoSenhaTemporaria(Instant.now().plusSeconds(15 * 60));
        usuarioVerificador.get().setSenhaTemporaria(String.valueOf(senhaTemporaria));
        usuarioVerificador.get().setContador(usuarioVerificador.get().getContador() + 1);

        return usuarioVerificador;
    }
/*
    public byte[] recuperarPerfil(String id) {

        Usuarios usuarios = usuarioRepository.findById(Long.parseLong(id)).orElseThrow( () -> new EntityNotFoundException("Usuário não encontrado"));
        return usuarios.getImagemPerfil();
    }*/

    public ResponseEditarUsuarioDto recuperarPerfil(String id) {

        Usuarios usuarios = usuarioRepository.findById(Long.parseLong(id)).orElseThrow( () -> new EntityNotFoundException("Usuário não encontrado"));
        ResponseEditarUsuarioDto editarUsuarioDto = new ResponseEditarUsuarioDto();
        editarUsuarioDto.setNome(usuarios.getNome());
        editarUsuarioDto.setImagem(usuarios.getImagemPerfil());
        editarUsuarioDto.setEmail(usuarios.getEmail());
        return editarUsuarioDto;
    }
}
