package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.dto.response.PerfilResumoDTO;
import com.lucas.slbackend.dto.response.PessoaComPerfisDTO;
import com.lucas.slbackend.dto.response.PessoaResponseDTO;
import com.lucas.slbackend.exception.NotFoundException;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaService {
  private final PessoaRepository repository;

  @Transactional(readOnly = true)
  public Page<Pessoa> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  // listar como DTO
  @Transactional(readOnly = true)
  public Page<PessoaResponseDTO> listDTO(Pageable pageable) {
    return repository.findAll(pageable)
        .map(this::toResponse);
  }

  @Transactional(readOnly = true)
  public Pessoa get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Pessoa not found"));
  }

  // get simples em DTO
  @Transactional(readOnly = true)
  public PessoaResponseDTO getResponse(Long id) {
    return toResponse(get(id));
  }

  // get com perfis via fetch join
  @Transactional(readOnly = true)
  public PessoaComPerfisDTO getWithPerfisDTO(Long id) {
    Pessoa p = repository.findByIdWithPerfis(id).orElseThrow(() -> new NotFoundException("Pessoa not found"));
    var perfis = p.getPerfis().stream()
        .map(pp -> new PerfilResumoDTO(
            pp.getPerfil() != null ? pp.getPerfil().getId() : null,
            pp.getPerfil() != null ? pp.getPerfil().getTipo() : null))
        .toList();
    return new PessoaComPerfisDTO(p.getId(), p.getNome(), p.getEmail(), p.getAtivo(), perfis);
  }

  @Transactional
  public Pessoa create(Pessoa entity) {
    entity.setId(null);
    return repository.save(entity);
  }

  @Transactional
  public Pessoa update(Long id, Pessoa updates) {
    Pessoa existing = get(id);
    // copy allowed fields
    existing.setNome(updates.getNome());
    existing.setEmail(updates.getEmail());
    existing.setSenha(updates.getSenha());
    existing.setCodigoValidacao(updates.getCodigoValidacao());
    existing.setValidadeCodigoValidacao(updates.getValidadeCodigoValidacao());
    existing.setAtivo(updates.getAtivo());
    existing.setFotoPerfil(updates.getFotoPerfil());
    return repository.save(existing);
  }

  @Transactional
  public void delete(Long id) {
    Pessoa existing = get(id);
    repository.delete(existing);
  }

  public PessoaResponseDTO toResponse(Pessoa p) {
    return new PessoaResponseDTO(p.getId(), p.getNome(), p.getEmail(), p.getAtivo());
  }
}
