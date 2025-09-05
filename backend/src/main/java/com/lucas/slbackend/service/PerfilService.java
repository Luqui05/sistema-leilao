package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.dto.response.PerfilResponseDTO;
import com.lucas.slbackend.exception.NotFoundException;
import com.lucas.slbackend.model.Perfil;
import com.lucas.slbackend.repository.PerfilRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfilService {
  private final PerfilRepository repository;

  @Transactional(readOnly = true)
  public Page<Perfil> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  // listar em DTO
  @Transactional(readOnly = true)
  public Page<PerfilResponseDTO> listDTO(Pageable pageable) {
    return repository.findAll(pageable)
        .map(this::toResponse);
  }

  @Transactional(readOnly = true)
  public Perfil get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Perfil not found"));
  }

  // get em DTO
  @Transactional(readOnly = true)
  public PerfilResponseDTO getResponse(Long id) {
    return toResponse(get(id));
  }

  @Transactional
  public Perfil create(Perfil entity) {
    entity.setId(null);
    return repository.save(entity);
  }

  @Transactional
  public Perfil update(Long id, Perfil updates) {
    Perfil existing = get(id);
    existing.setTipo(updates.getTipo());
    return repository.save(existing);
  }

  @Transactional
  public void delete(Long id) {
    repository.delete(get(id));
  }

  // Helper
  public PerfilResponseDTO toResponse(Perfil p) {
    return new PerfilResponseDTO(p.getId(), p.getTipo());
  }
}