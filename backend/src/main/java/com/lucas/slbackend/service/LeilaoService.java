package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.dto.response.LeilaoResponseDTO;
import com.lucas.slbackend.dto.response.LeilaoResumoDTO;
import com.lucas.slbackend.exception.NotFoundException;
import com.lucas.slbackend.model.Categoria;
import com.lucas.slbackend.model.Leilao;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.repository.CategoriaRepository;
import com.lucas.slbackend.repository.LeilaoRepository;
import com.lucas.slbackend.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeilaoService {
  private final LeilaoRepository repository;
  private final CategoriaRepository categoriaRepository;
  private final PessoaRepository pessoaRepository;

  @Transactional(readOnly = true)
  public Page<Leilao> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  // lista em DTO resumido
  @Transactional(readOnly = true)
  public Page<LeilaoResumoDTO> listDTO(Pageable pageable) {
    return repository.findAll(pageable)
        .map(l -> new LeilaoResumoDTO(
            l.getId(),
            l.getTitulo(),
            l.getStatus(),
            l.getCategoria() != null ? l.getCategoria().getId() : null));
  }

  @Transactional(readOnly = true)
  public Leilao get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Leilao not found"));
  }

  // get detalhado em DTO (sem navegar associações LAZY)
  @Transactional(readOnly = true)
  public LeilaoResponseDTO getDTO(Long id) {
    return toResponse(get(id));
  }

  @Transactional
  public Leilao create(Leilao entity) {
    entity.setId(null);
    if (entity.getCategoria() == null || entity.getCategoria().getId() == null) {
      throw new NotFoundException("Categoria reference required");
    }
    Categoria cat = categoriaRepository.findById(entity.getCategoria().getId())
        .orElseThrow(() -> new NotFoundException("Categoria not found"));
    entity.setCategoria(cat);

    if (entity.getAutor() != null && entity.getAutor().getId() != null) {
      Pessoa autor = pessoaRepository.findById(entity.getAutor().getId())
          .orElseThrow(() -> new NotFoundException("Pessoa (autor) not found"));
      entity.setAutor(autor);
    } else {
      entity.setAutor(null);
    }
    return repository.save(entity);
  }

  // helper para mapear entidade -> DTO
  public LeilaoResponseDTO toResponse(Leilao l) {
    return new LeilaoResponseDTO(
        l.getId(),
        l.getTitulo(),
        l.getDescricao(),
        l.getDescricaoDetalhada(),
        l.getDataHoraInicio(),
        l.getDataHoraFim(),
        l.getStatus(),
        l.getObservacao(),
        l.getValorIncremento(),
        l.getLanceMinimo(),
        l.getCategoria() != null ? l.getCategoria().getId() : null,
        l.getAutor() != null ? l.getAutor().getId() : null
    );
  }

  @Transactional
  public Leilao update(Long id, Leilao updates) {
    Leilao existing = get(id);
    existing.setTitulo(updates.getTitulo());
    existing.setDescricao(updates.getDescricao());
    existing.setDescricaoDetalhada(updates.getDescricaoDetalhada());
    existing.setDataHoraInicio(updates.getDataHoraInicio());
    existing.setDataHoraFim(updates.getDataHoraFim());
    existing.setStatus(updates.getStatus());
    existing.setObservacao(updates.getObservacao());
    existing.setValorIncremento(updates.getValorIncremento());
    existing.setLanceMinimo(updates.getLanceMinimo());

    if (updates.getCategoria() != null && updates.getCategoria().getId() != null) {
      Categoria cat = categoriaRepository.findById(updates.getCategoria().getId())
          .orElseThrow(() -> new NotFoundException("Categoria not found"));
      existing.setCategoria(cat);
    }
    if (updates.getAutor() != null && updates.getAutor().getId() != null) {
      Pessoa autor = pessoaRepository.findById(updates.getAutor().getId())
          .orElseThrow(() -> new NotFoundException("Pessoa (autor) not found"));
      existing.setAutor(autor);
    } else if (updates.getAutor() == null) {
      existing.setAutor(null);
    }
    return repository.save(existing);
  }

  @Transactional
  public void delete(Long id) {
    repository.delete(get(id));
  }
}
