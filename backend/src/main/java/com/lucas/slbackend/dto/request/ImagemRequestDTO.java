package com.lucas.slbackend.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImagemRequestDTO(
  @NotBlank String nomeImagem,
  LocalDateTime dataHoraCadastro,
  @NotNull Long leilaoId
) {

}