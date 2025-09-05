package com.lucas.slbackend.dto.response;

import java.time.LocalDateTime;

public record LanceResponse(
    Long id,
    Float valorLance,
    LocalDateTime dataHora,
    Long leilaoId,
    Long autorId) {
}
