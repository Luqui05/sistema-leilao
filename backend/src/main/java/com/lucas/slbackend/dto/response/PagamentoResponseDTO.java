package com.lucas.slbackend.dto.response;

import java.time.LocalDateTime;

public record PagamentoResponseDTO(
    Long id,
    Float valor,
    LocalDateTime dataHora,
    String status,
    Long leilaoId) {

}
