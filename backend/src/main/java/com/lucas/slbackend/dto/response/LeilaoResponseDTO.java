package com.lucas.slbackend.dto.response;

import java.time.LocalDateTime;

import com.lucas.slbackend.enums.StatusLeilao;

public record LeilaoResponseDTO(
  Long id,
  String titulo,
  String descricao,
  String descricaoDetalhada,
  LocalDateTime dataHoraInicio,
  LocalDateTime dataHoraFim,
  StatusLeilao status,
  String observacao,
  Float valorIncremento,
  Float lanceMinimo,
  Long categoriaId,
  Long autorId
) {}