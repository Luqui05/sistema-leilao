package com.lucas.slbackend.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LanceRequestDTO(
    @NotNull @Positive Float valorLance,
    LocalDateTime dataHora,
    @NotNull Long leilaoId,
    @NotNull Long autorId
) {}