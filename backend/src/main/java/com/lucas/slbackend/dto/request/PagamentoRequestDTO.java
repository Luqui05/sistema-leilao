package com.lucas.slbackend.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PagamentoRequestDTO(
    @NotNull @Positive Float valor,
    LocalDateTime dataHora,
    String status,
    @NotNull Long leilaoId) {

}
