package com.lucas.slbackend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Feedback> list(Pageable pageable) { return repository.findAll(pageable); }

    @Transactional(readOnly = true)
    public Feedback get(Long id) { return repository.findById(id).orElseThrow(() -> new NotFoundException("Feedback not found")); }

    @Transactional
    public Feedback create(Feedback entity) {
        entity.setId(null);
        if (entity.getAutor() != null && entity.getAutor().getId() != null) {
            Pessoa autor = pessoaRepository.findById(entity.getAutor().getId()).orElseThrow(() -> new NotFoundException("Pessoa (autor) not found"));
            entity.setAutor(autor);
        } else {
            entity.setAutor(null);
        }
        if (entity.getDestinatario() != null && entity.getDestinatario().getId() != null) {
            Pessoa dest = pessoaRepository.findById(entity.getDestinatario().getId()).orElseThrow(() -> new NotFoundException("Pessoa (destinatario) not found"));
            entity.setDestinatario(dest);
        } else {
            entity.setDestinatario(null);
        }
        return repository.save(entity);
    }

    @Transactional
    public Feedback update(Long id, Feedback updates) {
        Feedback existing = get(id);
        existing.setComentario(updates.getComentario());
        existing.setNota(updates.getNota());
        existing.setDataHora(updates.getDataHora());
        if (updates.getAutor() != null && updates.getAutor().getId() != null) {
            Pessoa autor = pessoaRepository.findById(updates.getAutor().getId()).orElseThrow(() -> new NotFoundException("Pessoa (autor) not found"));
            existing.setAutor(autor);
        } else if (updates.getAutor() == null) {
            existing.setAutor(null);
        }
        if (updates.getDestinatario() != null && updates.getDestinatario().getId() != null) {
            Pessoa dest = pessoaRepository.findById(updates.getDestinatario().getId()).orElseThrow(() -> new NotFoundException("Pessoa (destinatario) not found"));
            existing.setDestinatario(dest);
        } else if (updates.getDestinatario() == null) {
            existing.setDestinatario(null);
        }
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) { repository.delete(get(id)); }
}
