package com.lucas.slbackend.dto.response;

import com.lucas.slbackend.enums.TipoPerfil;

public record PerfilResponseDTO(
    Long id,
    TipoPerfil tipo) {
}