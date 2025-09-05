package com.lucas.slbackend.dto.response;

import com.lucas.slbackend.enums.StatusLeilao;

public record LeilaoResumoDTO(Long id, String titulo, StatusLeilao status, Long categoriaId) {
}
