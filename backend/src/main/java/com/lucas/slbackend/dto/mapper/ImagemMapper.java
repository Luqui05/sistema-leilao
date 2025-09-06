package com.lucas.slbackend.dto.mapper;

import com.lucas.slbackend.dto.request.ImagemRequestDTO;
import com.lucas.slbackend.dto.response.ImagemResponse;
import com.lucas.slbackend.model.Imagem;
import com.lucas.slbackend.model.Leilao;

public class ImagemMapper {
  public static ImagemResponse toResponse(Imagem i) {
    Long leilaoId = (i.getLeilao() != null ? i.getLeilao().getId() : null);
    return new ImagemResponse(i.getId(), i.getNomeImagem(), i.getDataHoraCadastro(), leilaoId);
  }

  public static Imagem toEntity(ImagemRequestDTO dto, Leilao leilao) {
    Imagem i = new Imagem();
    i.setNomeImagem(dto.nomeImagem());
    i.setDataHoraCadastro(dto.dataHoraCadastro());
    i.setLeilao(leilao);
    return i;
  }

  public static void updateEntity(Imagem entity, ImagemRequestDTO dto, Leilao leilao) {
    entity.setNomeImagem(dto.nomeImagem());
    entity.setDataHoraCadastro(dto.dataHoraCadastro());
    entity.setLeilao(leilao);
  }
}
