package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.dto.request.PagamentoRequestDTO;
import com.lucas.slbackend.exception.NotFoundException;
import com.lucas.slbackend.model.Pagamento;
import com.lucas.slbackend.model.Leilao;
import com.lucas.slbackend.repository.PagamentoRepository;
import com.lucas.slbackend.repository.LeilaoRepository;
import com.lucas.slbackend.dto.mapper.PagamentoMapper;

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
  public Pagamento create(PagamentoRequestDTO dto) {
    Leilao leilao = leilaoRepository.findById(dto.leilaoId())
        .orElseThrow(() -> new NotFoundException("Leilao not found"));
    Pagamento pagamento = PagamentoMapper.toEntity(dto, leilao);
    return repository.save(pagamento);
  }

  @Transactional
  public Pagamento update(Long id, PagamentoRequestDTO dto) {
    Pagamento existing = get(id);
    Leilao leilao = leilaoRepository.findById(dto.leilaoId())
        .orElseThrow(() -> new NotFoundException("Leilao not found"));
    existing.setValor(dto.valor());
    existing.setDataHora(dto.dataHora());
    existing.setStatus(dto.status());
    existing.setLeilao(leilao);
    return repository.save(existing);
  }

  @Transactional
  public void delete(Long id) {
    repository.delete(get(id));
  }
}
