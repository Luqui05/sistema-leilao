package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.dto.request.CategoriaRequestDTO;
import com.lucas.slbackend.dto.response.CategoriaComLeiloesDTO;
import com.lucas.slbackend.dto.response.CategoriaResponseDTO;
import com.lucas.slbackend.dto.response.LeilaoResumoDTO;
import com.lucas.slbackend.exception.NotFoundException;
import com.lucas.slbackend.model.Categoria;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.repository.CategoriaRepository;
import com.lucas.slbackend.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
  private final CategoriaRepository repository;
  private final PessoaRepository pessoaRepository;

  @Transactional(readOnly = true)
  public Page<Categoria> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  // versão em DTO para listar
  @Transactional(readOnly = true)
  public Page<CategoriaResponseDTO> listDTO(Pageable pageable) {
    return repository.findAll(pageable)
        .map(c -> new CategoriaResponseDTO(c.getId(), c.getNome()));
  }

  @Transactional(readOnly = true)
  public Categoria get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Categoria not found"));
  }

  // versão em DTO para get simples (sem leilões)
  @Transactional(readOnly = true)
  public CategoriaResponseDTO getResponse(Long id) {
    Categoria c = get(id);
    return new CategoriaResponseDTO(c.getId(), c.getNome());
  }

  // get com leilões via fetch join e DTO compacto dos leilões
  @Transactional(readOnly = true)
  public CategoriaComLeiloesDTO getWithLeiloesDTO(Long id) {
    Categoria c = repository.findByIdWithLeiloes(id).orElseThrow(() -> new NotFoundException("Categoria not found"));
    var leiloes = c.getLeiloes().stream()
        .map(l -> new LeilaoResumoDTO(
            l.getId(),
            l.getTitulo(),
            l.getStatus(),
            l.getCategoria() != null ? l.getCategoria().getId() : null))
        .toList();
    return new CategoriaComLeiloesDTO(c.getId(), c.getNome(), leiloes);
  }

  @Transactional
  public Categoria create(CategoriaRequestDTO dto) {
    Categoria c = new Categoria();
    c.setNome(dto.nome());
    return repository.save(c);
  }

  @Transactional
  public Categoria update(Long id, Categoria updates) {
    Categoria existing = get(id);
    existing.setNome(updates.getNome());
    existing.setObservacao(updates.getObservacao());
    if (updates.getCriador() != null && updates.getCriador().getId() != null) {
      Pessoa criador = pessoaRepository.findById(updates.getCriador().getId())
          .orElseThrow(() -> new NotFoundException("Pessoa (criador) not found"));
      existing.setCriador(criador);
    }
    return repository.save(existing);
  }

  @Transactional
  public void delete(Long id) {
    repository.delete(get(id));
  }
}
