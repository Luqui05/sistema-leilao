package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.exception.NotFoundException;
import com.lucas.slbackend.model.Imagem;
import com.lucas.slbackend.model.Leilao;
import com.lucas.slbackend.repository.ImagemRepository;
import com.lucas.slbackend.repository.LeilaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagemService {
  private final ImagemRepository repository;
  private final LeilaoRepository leilaoRepository;

  @Transactional(readOnly = true)
  public Page<Imagem> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Imagem get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Imagem not found"));
  }

  @Transactional
  public Imagem create(Imagem entity) {
    entity.setId(null);
    if (entity.getLeilao() != null && entity.getLeilao().getId() != null) {
      Leilao l = leilaoRepository.findById(entity.getLeilao().getId())
          .orElseThrow(() -> new NotFoundException("Leilao not found"));
      entity.setLeilao(l);
    } else if (entity.getLeilao() != null) {
      throw new NotFoundException("Leilao reference required");
    }
    return repository.save(entity);
  }

  @Transactional
  public Imagem update(Long id, Imagem updates) {
    Imagem existing = get(id);
    existing.setDataHoraCadastro(updates.getDataHoraCadastro());
    existing.setNomeImagem(updates.getNomeImagem());
    if (updates.getLeilao() != null && updates.getLeilao().getId() != null) {
      Leilao l = leilaoRepository.findById(updates.getLeilao().getId())
          .orElseThrow(() -> new NotFoundException("Leilao not found"));
      existing.setLeilao(l);
    }
    return repository.save(existing);
  }

  @Transactional
  public void delete(Long id) {
    repository.delete(get(id));
  }
}
