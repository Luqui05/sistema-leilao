package com.lucas.slbackend.dto.mapper;

import com.lucas.slbackend.dto.response.ImagemResponse;
import com.lucas.slbackend.model.Imagem;

public class ImagemMapper {
  public static ImagemResponse toResponse(Imagem i) {
    Long leilaoId = (i.getLeilao() != null ? i.getLeilao().getId() : null);
    return new ImagemResponse(i.getId(), i.getNomeImagem(), i.getDataHoraCadastro(), leilaoId);
  }
}
