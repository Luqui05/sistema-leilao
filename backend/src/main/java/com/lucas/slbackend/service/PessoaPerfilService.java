package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.dto.request.PessoaPerfilRequestDTO;
import com.lucas.slbackend.dto.response.PessoaPerfilResponseDTO;
import com.lucas.slbackend.exception.NotFoundException;
import com.lucas.slbackend.model.Perfil;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.model.PessoaPerfil;
import com.lucas.slbackend.repository.PerfilRepository;
import com.lucas.slbackend.repository.PessoaPerfilRepository;
import com.lucas.slbackend.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaPerfilService {
  private final PessoaPerfilRepository repository;
  private final PessoaRepository pessoaRepository;
  private final PerfilRepository perfilRepository;

  @Transactional(readOnly = true)
  public Page<PessoaPerfil> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  // listar em DTO
  @Transactional(readOnly = true)
  public Page<PessoaPerfilResponseDTO> listDTO(Pageable pageable) {
    return repository.findAll(pageable)
        .map(pp -> new PessoaPerfilResponseDTO(
            pp.getId(),
            pp.getPessoa() != null ? pp.getPessoa().getId() : null,
            pp.getPerfil() != null ? pp.getPerfil().getId() : null
        ));
  }

  @Transactional(readOnly = true)
  public PessoaPerfil get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("PessoaPerfil not found"));
  }

  // get em DTO
  @Transactional(readOnly = true)
  public PessoaPerfilResponseDTO getDTO(Long id) {
    PessoaPerfil pp = get(id);
    return new PessoaPerfilResponseDTO(
        pp.getId(),
        pp.getPessoa() != null ? pp.getPessoa().getId() : null,
        pp.getPerfil() != null ? pp.getPerfil().getId() : null
    );
  }

  // create com DTO de request
  @Transactional
  public PessoaPerfilResponseDTO create(PessoaPerfilRequestDTO dto) {
    Pessoa pessoa = pessoaRepository.findById(dto.pessoaId())
        .orElseThrow(() -> new NotFoundException("Pessoa not found"));
    Perfil perfil = perfilRepository.findById(dto.perfilId())
        .orElseThrow(() -> new NotFoundException("Perfil not found"));
    PessoaPerfil entity = new PessoaPerfil();
    entity.setPessoa(pessoa);
    entity.setPerfil(perfil);
    PessoaPerfil saved = repository.save(entity);
    return new PessoaPerfilResponseDTO(saved.getId(), pessoa.getId(), perfil.getId());
  }

  // update com DTO de request
  @Transactional
  public PessoaPerfilResponseDTO update(Long id, PessoaPerfilRequestDTO dto) {
    PessoaPerfil existing = get(id);
    if (dto.pessoaId() != null) {
      Pessoa p = pessoaRepository.findById(dto.pessoaId())
          .orElseThrow(() -> new NotFoundException("Pessoa not found"));
      existing.setPessoa(p);
    }
    if (dto.perfilId() != null) {
      Perfil pe = perfilRepository.findById(dto.perfilId())
          .orElseThrow(() -> new NotFoundException("Perfil not found"));
      existing.setPerfil(pe);
    }
    PessoaPerfil updated = repository.save(existing);
    return new PessoaPerfilResponseDTO(
        updated.getId(),
        updated.getPessoa() != null ? updated.getPessoa().getId() : null,
        updated.getPerfil() != null ? updated.getPerfil().getId() : null
    );
  }

  @Transactional
  public void delete(Long id) {
    repository.delete(get(id));
  }
}