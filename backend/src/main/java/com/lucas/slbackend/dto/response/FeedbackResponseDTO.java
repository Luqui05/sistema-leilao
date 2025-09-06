package com.lucas.slbackend.dto.response;

import java.time.LocalDateTime;

public record FeedbackResponseDTO(
    Long id,
    String comentario,
    Integer nota,
    LocalDateTime dataHora,
    Long autorId,
    Long destinatarioId) {
}