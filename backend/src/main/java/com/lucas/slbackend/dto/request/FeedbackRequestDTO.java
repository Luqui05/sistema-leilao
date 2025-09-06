package com.lucas.slbackend.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeedbackRequestDTO(
    @Size(max = 500) String comentario,
    @NotNull @Min(1) @Max(5) Integer nota,
    LocalDateTime dataHora,
    @NotNull Long autorId,
    @NotNull Long destinatarioId
) {}