package com.lucas.slbackend.dto.request;

import com.lucas.slbackend.enums.TipoPerfil;

import jakarta.validation.constraints.NotNull;

public record PerfilRequestDTO(
    @NotNull TipoPerfil tipo) {

}
