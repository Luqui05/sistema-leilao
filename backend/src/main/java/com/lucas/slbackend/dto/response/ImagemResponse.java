package com.lucas.slbackend.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImagemResponse {
  private Long id;
  private String nomeImagem;
  private LocalDateTime dataHoraCadastro;
  private Long leilaoId;
}
