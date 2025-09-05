package com.lucas.slbackend.dto.response;

public record PessoaResponseDTO(
    Long id,
    String nome,
    String email,
    Boolean ativo) {
}
