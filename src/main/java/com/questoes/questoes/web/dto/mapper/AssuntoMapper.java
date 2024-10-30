package com.questoes.questoes.web.dto.mapper;

import com.questoes.questoes.entity.Assunto;
import com.questoes.questoes.entity.Disciplina;
import com.questoes.questoes.web.dto.assunto.AssuntoDTO;
import com.questoes.questoes.web.dto.disciplina.DisciplinaDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AssuntoMapper {
    public static Assunto toAssunto(AssuntoDTO create) {
        return new ModelMapper().map(create, Assunto.class);
    }

    public static AssuntoDTO toDto(Assunto createDto) {
        return new ModelMapper().map(createDto, AssuntoDTO.class);
    }
    public static List<AssuntoDTO> toListDto(List<Assunto> assuntos) {
        return assuntos.stream().map(assunto -> toDto(assunto)).collect(Collectors.toList());
    }
}
