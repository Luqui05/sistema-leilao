package com.lucas.slbackend.dto.mapper;

import com.lucas.slbackend.dto.request.LanceRequestDTO;
import com.lucas.slbackend.dto.response.LanceResponse;
import com.lucas.slbackend.model.Lance;
import com.lucas.slbackend.model.Leilao;
import com.lucas.slbackend.model.Pessoa;

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

  public static Lance toEntity(LanceRequestDTO dto, Leilao leilao, Pessoa autor) {
    Lance l = new Lance();
    l.setValorLance(dto.valorLance());
    l.setDataHora(dto.dataHora());
    l.setLeilao(leilao);
    l.setAutor(autor);
    return l;
  }

  public static void updateEntity(Lance entity, LanceRequestDTO dto, Leilao leilao, Pessoa autor) {
    entity.setValorLance(dto.valorLance());
    entity.setDataHora(dto.dataHora());
    entity.setLeilao(leilao);
    entity.setAutor(autor);
  }
}