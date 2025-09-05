package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.exception.NotFoundException;
import com.lucas.slbackend.model.Lance;
import com.lucas.slbackend.model.Leilao;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.repository.LanceRepository;
import com.lucas.slbackend.repository.LeilaoRepository;
import com.lucas.slbackend.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanceService {
  private final LanceRepository repository;
  private final LeilaoRepository leilaoRepository;
  private final PessoaRepository pessoaRepository;

  @Transactional(readOnly = true)
  public Page<Lance> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Lance get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Lance not found"));
  }

  @Transactional
  public Lance create(Lance entity) {
    entity.setId(null);
    if (entity.getLeilao() == null || entity.getLeilao().getId() == null) {
      throw new NotFoundException("Leilao reference required");
    }
    Leilao l = leilaoRepository.findById(entity.getLeilao().getId())
        .orElseThrow(() -> new NotFoundException("Leilao not found"));
    entity.setLeilao(l);

    if (entity.getAutor() != null && entity.getAutor().getId() != null) {
      Pessoa p = pessoaRepository.findById(entity.getAutor().getId())
          .orElseThrow(() -> new NotFoundException("Pessoa (autor) not found"));
      entity.setAutor(p);
    } else {
      entity.setAutor(null);
    }
    return repository.save(entity);
  }

  @Transactional
  public Lance update(Long id, Lance updates) {
    Lance existing = get(id);
    existing.setValorLance(updates.getValorLance());
    existing.setDataHora(updates.getDataHora());
    if (updates.getLeilao() != null && updates.getLeilao().getId() != null) {
      Leilao l = leilaoRepository.findById(updates.getLeilao().getId())
          .orElseThrow(() -> new NotFoundException("Leilao not found"));
      existing.setLeilao(l);
    }
    if (updates.getAutor() != null && updates.getAutor().getId() != null) {
      Pessoa p = pessoaRepository.findById(updates.getAutor().getId())
          .orElseThrow(() -> new NotFoundException("Pessoa (autor) not found"));
      existing.setAutor(p);
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
