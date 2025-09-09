package com.lucas.slbackend.dto.mapper;

import com.lucas.slbackend.dto.request.PerfilRequestDTO;
import com.lucas.slbackend.model.Perfil;

public class PerfilMapper {
    public static Perfil toEntity(PerfilRequestDTO dto) {
        Perfil p = new Perfil();
        p.setTipo(dto.tipo());
        return p;
    }

    public static void updateEntity(Perfil entity, PerfilRequestDTO dto) {
        entity.setTipo(dto.tipo());
    }
}
