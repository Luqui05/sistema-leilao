package com.lucas.slbackend.dto.mapper;

import java.util.List;

import com.lucas.slbackend.dto.request.PessoaRequestDTO;
import com.lucas.slbackend.dto.response.PerfilResumoDTO;
import com.lucas.slbackend.dto.response.PessoaComPerfisDTO;
import com.lucas.slbackend.dto.response.PessoaResponseDTO;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.model.PessoaPerfil;

public final class PessoaMapper {

  private PessoaMapper() {}

  public static PessoaResponseDTO toResponse(Pessoa p) {
    if (p == null) return null;
    return new PessoaResponseDTO(p.getId(), p.getNome(), p.getEmail(), p.getAtivo());
  }

  public static PerfilResumoDTO toPerfilResumo(PessoaPerfil pp) {
    if (pp == null || pp.getPerfil() == null) {
      return new PerfilResumoDTO(null, null);
    }
    return new PerfilResumoDTO(pp.getPerfil().getId(), pp.getPerfil().getTipo());
  }

  public static PessoaComPerfisDTO toComPerfis(Pessoa p) {
    if (p == null) return null;
    List<PerfilResumoDTO> perfis = p.getPerfis() == null
        ? List.of()
        : p.getPerfis().stream()
            .map(PessoaMapper::toPerfilResumo)
            .toList();
    return new PessoaComPerfisDTO(p.getId(), p.getNome(), p.getEmail(), p.getAtivo(), perfis);
  }

  public static Pessoa toEntity(PessoaRequestDTO dto) {
    if (dto == null) return null;
    Pessoa p = new Pessoa();
    p.setNome(dto.nome());
    p.setEmail(dto.email());
    p.setSenha(dto.senha());
    return p;
  }

  public static void updateEntity(Pessoa entity, PessoaRequestDTO dto) {
    if (dto == null || entity == null) return;
    entity.setNome(dto.nome());
    entity.setEmail(dto.email());
    if (dto.senha() != null && !dto.senha().isBlank()) {
      entity.setSenha(dto.senha());
    }
  }
}