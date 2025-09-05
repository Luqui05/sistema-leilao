package com.lucas.slbackend.dto.response;

import java.util.List;

public record CategoriaComLeiloesDTO(Long id, String nome, List<LeilaoResumoDTO> leiloes) {
}
