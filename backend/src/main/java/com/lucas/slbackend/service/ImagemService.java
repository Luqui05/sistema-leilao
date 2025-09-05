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
    private final ImagemRepository repo;
    private final LeilaoRepository leilaoRepo;

    @Transactional(readOnly = true)
    public Page<Imagem> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Imagem get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Imagem not found"));
    }

    @Transactional
    public Imagem create(Imagem body) {
        if (body == null || body.getLeilao() == null || body.getLeilao().getId() == null) {
            throw new IllegalArgumentException("leilao.id é obrigatório");
        }

        Long leilaoId = body.getLeilao().getId();
        Leilao leilao = leilaoRepo.findById(leilaoId)
            .orElseThrow(() -> new IllegalArgumentException("Leilão não encontrado: " + leilaoId));

        body.setId(null);
        body.setLeilao(leilao);

        return repo.save(body);
    }

    @Transactional
    public Imagem update(Long id, Imagem updates) {
        Imagem existing = get(id);
        existing.setDataHoraCadastro(updates.getDataHoraCadastro());
        existing.setNomeImagem(updates.getNomeImagem());
        if (updates.getLeilao() != null && updates.getLeilao().getId() != null) {
            Leilao l = leilaoRepo.findById(updates.getLeilao().getId())
                .orElseThrow(() -> new NotFoundException("Leilao not found"));
            existing.setLeilao(l);
        }
        return repo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        repo.delete(get(id));
    }
}
