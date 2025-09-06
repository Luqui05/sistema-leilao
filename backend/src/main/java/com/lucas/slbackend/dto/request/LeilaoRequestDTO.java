package com.lucas.slbackend.dto.request;

import java.time.LocalDateTime;

import com.lucas.slbackend.enums.StatusLeilao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LeilaoRequestDTO(
    @NotBlank String titulo,
    String descricao,
    String descricaoDetalhada,
    LocalDateTime dataHoraInicio,
    LocalDateTime dataHoraFim,
    StatusLeilao status,
    String observacao,
    @Positive Float valorIncremento,
    @Positive Float lanceMinimo,
    @NotNull Long categoriaId,
    Long autorId
) {}
