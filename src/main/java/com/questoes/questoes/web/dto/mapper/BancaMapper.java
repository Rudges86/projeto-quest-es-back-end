package com.questoes.questoes.web.dto.mapper;

import com.questoes.questoes.entity.Assunto;
import com.questoes.questoes.entity.Banca;
import com.questoes.questoes.web.dto.assunto.AssuntoDTO;
import com.questoes.questoes.web.dto.banca.BancaDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BancaMapper {
    public static Banca toBanca(BancaDto create) {
        return new ModelMapper().map(create, Banca.class);
    }

    public static BancaDto toDto(Banca createDto) {
        return new ModelMapper().map(createDto, BancaDto.class);
    }
    public static List<BancaDto> toListDto(List<Banca> bancas) {
        return bancas.stream().map(banca -> toDto(banca)).collect(Collectors.toList());
    }
}
