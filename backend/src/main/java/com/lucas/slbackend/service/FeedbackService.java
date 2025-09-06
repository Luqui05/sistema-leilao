package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.dto.mapper.FeedbackMapper;
import com.lucas.slbackend.dto.request.FeedbackRequestDTO;
import com.lucas.slbackend.dto.response.FeedbackResponseDTO;
import com.lucas.slbackend.exception.NotFoundException;
import com.lucas.slbackend.model.Feedback;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.repository.FeedbackRepository;
import com.lucas.slbackend.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {
  private final FeedbackRepository repository;
  private final PessoaRepository pessoaRepository;

  @Transactional(readOnly = true)
  public Page<FeedbackResponseDTO> list(Pageable pageable) {
    return repository.findAll(pageable)
      .map(FeedbackMapper::toResponseDTO);
  }

  @Transactional(readOnly = true)
  public FeedbackResponseDTO get(Long id) {
    Feedback feedback = repository.findById(id)
      .orElseThrow(() -> new NotFoundException("Feedback not found"));
    return FeedbackMapper.toResponseDTO(feedback);
  }

  @Transactional
  public FeedbackResponseDTO create(FeedbackRequestDTO dto) {
    Feedback entity = new Feedback();
    entity.setComentario(dto.comentario());
    entity.setNota(dto.nota());
    entity.setDataHora(dto.dataHora());

    Pessoa autor = pessoaRepository.findById(dto.autorId())
      .orElseThrow(() -> new NotFoundException("Pessoa (autor) not found"));
    entity.setAutor(autor);

    Pessoa destinatario = pessoaRepository.findById(dto.destinatarioId())
      .orElseThrow(() -> new NotFoundException("Pessoa (destinatario) not found"));
    entity.setDestinatario(destinatario);

    Feedback saved = repository.save(entity);
    return FeedbackMapper.toResponseDTO(saved);
  }

  @Transactional
  public FeedbackResponseDTO update(Long id, FeedbackRequestDTO dto) {
    Feedback existing = repository.findById(id)
      .orElseThrow(() -> new NotFoundException("Feedback not found"));

    existing.setComentario(dto.comentario());
    existing.setNota(dto.nota());
    existing.setDataHora(dto.dataHora());

    Pessoa autor = pessoaRepository.findById(dto.autorId())
      .orElseThrow(() -> new NotFoundException("Pessoa (autor) not found"));
    existing.setAutor(autor);

    Pessoa destinatario = pessoaRepository.findById(dto.destinatarioId())
      .orElseThrow(() -> new NotFoundException("Pessoa (destinatario) not found"));
    existing.setDestinatario(destinatario);

    Feedback saved = repository.save(existing);
    return FeedbackMapper.toResponseDTO(saved);
  }

  @Transactional
  public void delete(Long id) {
    Feedback feedback = repository.findById(id)
      .orElseThrow(() -> new NotFoundException("Feedback not found"));
    repository.delete(feedback);
  }
}
