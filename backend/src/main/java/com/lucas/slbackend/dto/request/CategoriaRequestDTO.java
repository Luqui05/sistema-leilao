package com.lucas.slbackend.dto.request;

import jakarta.validation.constraints.NotNull;

public record CategoriaRequestDTO(
  @NotNull String nome,
  String observacao
  ) {
}
