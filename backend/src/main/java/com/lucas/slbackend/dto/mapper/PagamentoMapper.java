package com.lucas.slbackend.dto.mapper;

import com.lucas.slbackend.dto.request.PagamentoRequestDTO;
import com.lucas.slbackend.dto.response.PagamentoResponseDTO;
import com.lucas.slbackend.model.Leilao;
import com.lucas.slbackend.model.Pagamento;

public class PagamentoMapper {

  public static PagamentoResponseDTO toResponse(Pagamento p) {
    return new PagamentoResponseDTO(
        p.getId(),
        p.getValor(),
        p.getDataHora(),
        p.getStatus(),
        p.getLeilao() != null ? p.getLeilao().getId() : null);
  }

  public static Pagamento toEntity(PagamentoRequestDTO dto, Leilao leilao) {
    Pagamento p = new Pagamento();
    p.setValor(dto.valor());
    p.setDataHora(dto.dataHora());
    p.setStatus(dto.status());
    p.setLeilao(leilao);
    return p;
  }
}
