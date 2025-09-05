package com.lucas.slbackend.dto.response;

import java.util.List;

public record PessoaComPerfisDTO(
    Long id,
    String nome,
    String email,
    Boolean ativo,
    List<PerfilResumoDTO> perfis
) {}