package com.questoes.questoes.web.dto.mapper;

import com.questoes.questoes.entity.usuario.Usuarios;
import com.questoes.questoes.web.dto.usuario.CadastrarUsuarioDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {
    public static Usuarios toUsuario(CadastrarUsuarioDTO createDto) {
        return new ModelMapper().map(createDto, Usuarios.class);
    }

    /*public static UsuarioResponseDto toDto(Usuarios usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> toListDto(List<Usuarios> usuarios) {
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }*/
}