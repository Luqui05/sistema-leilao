package com.lucas.slbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
  @NotBlank String senhaAtual,
  @NotBlank @Size(min = 6) String novaSenha
) {}
