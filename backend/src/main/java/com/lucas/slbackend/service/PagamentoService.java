package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.exception.NotFoundException;
import com.lucas.slbackend.model.Pagamento;
import com.lucas.slbackend.model.Leilao;
import com.lucas.slbackend.repository.PagamentoRepository;
import com.lucas.slbackend.repository.LeilaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagamentoService {
  private final PagamentoRepository repository;
  private final LeilaoRepository leilaoRepository;

  @Transactional(readOnly = true)
  public Page<Pagamento> list(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Pagamento get(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException("Pagamento not found"));
  }

  @Transactional
  public Pagamento create(Pagamento entity) {
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
  public Pagamento update(Long id, Pagamento updates) {
    Pagamento existing = get(id);
    existing.setValor(updates.getValor());
    existing.setDataHora(updates.getDataHora());
    existing.setStatus(updates.getStatus());
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
