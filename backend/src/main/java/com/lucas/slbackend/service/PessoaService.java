package com.lucas.slbackend.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.dto.mapper.PessoaMapper;
import com.lucas.slbackend.dto.response.PessoaComPerfisDTO;
import com.lucas.slbackend.dto.response.PessoaResponseDTO;
import com.lucas.slbackend.enums.TipoPerfil;
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
public class PessoaService {
  private final PessoaRepository repository;
  private final PessoaPerfilRepository pessoaPerfilRepository;
  private final PerfilRepository perfilRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public Page<Pessoa> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  // listar como DTO
  @Transactional(readOnly = true)
  public Page<PessoaResponseDTO> listDTO(Pageable pageable) {
    return repository.findAll(pageable)
        .map(com.lucas.slbackend.dto.mapper.PessoaMapper::toResponse);
  }

  @Transactional(readOnly = true)
  public Pessoa get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Pessoa not found"));
  }

  // get simples em DTO
  @Transactional(readOnly = true)
  public PessoaResponseDTO getResponse(Long id) {
    return PessoaMapper.toResponse(get(id));
  }

  // get com perfis via fetch join
  @Transactional(readOnly = true)
  public PessoaComPerfisDTO getWithPerfisDTO(Long id) {
    Pessoa p = repository.findByIdWithPerfis(id).orElseThrow(() -> new NotFoundException("Pessoa not found"));
    return PessoaMapper.toComPerfis(p);
  }

  @Transactional
  public Pessoa create(Pessoa entity) {
    entity.setId(null);
    // e-mail único
    if (repository.findByEmail(entity.getEmail()).isPresent()) {
      throw new DataIntegrityViolationException("E-mail already registered");
    }
    // codificar senha
    if (entity.getSenha() != null) {
      entity.setSenha(passwordEncoder.encode(entity.getSenha()));
    }
    Pessoa saved = repository.save(entity);

    // Garante pelo menos um perfil (COMPRADOR por padrão)
    if (saved.getPerfis() == null || saved.getPerfis().isEmpty()) {
      attachPerfil(saved, TipoPerfil.COMPRADOR);
    }
    return saved;
  }

  @Transactional
  public Pessoa update(Long id, Pessoa updates) {
    Pessoa existing = get(id);
    existing.setNome(updates.getNome());
    existing.setEmail(updates.getEmail());
    if (updates.getSenha() != null && !updates.getSenha().isBlank()
        && !updates.getSenha().equals(existing.getSenha())) {
      existing.setSenha(encodeIfPlain(updates.getSenha()));
    }
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

  // anexar perfil(is) por tipo
  @Transactional
  public void attachPerfil(Pessoa pessoa, TipoPerfil tipo) {
    Perfil perfil = perfilRepository.findByTipo(tipo)
        .orElseGet(() -> {
          Perfil pe = new Perfil();
          pe.setTipo(tipo);
          return perfilRepository.save(pe);
        });
    PessoaPerfil pp = new PessoaPerfil();
    pp.setPessoa(pessoa);
    pp.setPerfil(perfil);
    pessoaPerfilRepository.save(pp);
  }

  private String encodeIfPlain(String rawOrEncoded) {
    // se começar com o marcador do bcrypt, assume que já está codificada
    if (rawOrEncoded.startsWith("$2a$") || rawOrEncoded.startsWith("$2b$") || rawOrEncoded.startsWith("$2y$")) {
      return rawOrEncoded;
    }
    return passwordEncoder.encode(rawOrEncoded);
  }
}
