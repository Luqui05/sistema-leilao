package com.lucas.slbackend.dto.mapper;

import com.lucas.slbackend.dto.response.LanceResponse;
import com.lucas.slbackend.model.Lance;

public final class LanceMapper {

  private LanceMapper() {}

  public static LanceResponse toResponse(Lance l) {
    if (l == null) return null;
    Long leilaoId = l.getLeilao() != null ? l.getLeilao().getId() : null;
    Long autorId = l.getAutor() != null ? l.getAutor().getId() : null;
    return new LanceResponse(
        l.getId(),
        l.getValorLance(),
        l.getDataHora(),
        leilaoId,
        autorId
    );
  }
}