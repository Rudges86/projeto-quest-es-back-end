package com.questoes.questoes.web.dto.mapper;

import com.questoes.questoes.entity.Disciplina;
import com.questoes.questoes.entity.usuario.Usuarios;
import com.questoes.questoes.web.dto.disciplina.DisciplinaDTO;
import com.questoes.questoes.web.dto.usuario.CadastrarUsuarioDTO;
import com.questoes.questoes.web.dto.usuario.UsuarioPerfilDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class DisciplinaMapper {
    public static Disciplina toDisciplina(DisciplinaDTO create) {
        return new ModelMapper().map(create, Disciplina.class);
    }

    public static DisciplinaDTO toDto(Disciplina createDto) {
        return new ModelMapper().map(createDto, DisciplinaDTO.class);
    }
    public static List<DisciplinaDTO> toListDto(List<Disciplina> disciplinas) {
        return disciplinas.stream().map(disciplina -> toDto(disciplina)).collect(Collectors.toList());
    }
}
