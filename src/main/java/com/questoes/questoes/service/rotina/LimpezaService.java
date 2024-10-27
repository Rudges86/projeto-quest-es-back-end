package com.questoes.questoes.service.rotina;

import com.questoes.questoes.entity.enumerate.status.StatusUsuario;
import com.questoes.questoes.entity.usuario.UsuarioVerificador;
import com.questoes.questoes.entity.usuario.Usuarios;
import com.questoes.questoes.repository.usuario.UsuarioRepository;
import com.questoes.questoes.repository.usuario.UsuarioVerificadorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class LimpezaService {
    @Autowired
    private UsuarioVerificadorRepository verificadorrepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    //@Scheduled(fixedRate = 900000)
    @Scheduled(fixedRate = 60000)
    public void limparUsuariosComTempoExpirado() {
        LocalDateTime agora = LocalDateTime.now();
       // List<UsuarioVerificador> usuariosExpirados = verificadorrepository.findByTimeExpiracao(agora);

        /* Fazer verificar os pendentes e deletar depois de 15 dias */
        LocalDateTime limite = agora.minusDays(15);
        List<Usuarios> pendentes = usuarioRepository.findByStatus(StatusUsuario.PENDENTE, agora);
//        usuarioRepository.deleteAll(pendentes);

        log.error(String.format("Registros expirados deletados: %s, Registros pendentes deletados: ", pendentes.size()));
    }

}
