package com.lucas.slbackend.dto.mapper;

import com.lucas.slbackend.dto.request.LeilaoRequestDTO;
import com.lucas.slbackend.model.Categoria;
import com.lucas.slbackend.model.Leilao;
import com.lucas.slbackend.model.Pessoa;

public class LeilaoMapper {
    public static Leilao toEntity(LeilaoRequestDTO dto, Categoria categoria, Pessoa autor) {
        Leilao l = new Leilao();
        l.setTitulo(dto.titulo());
        l.setDescricao(dto.descricao());
        l.setDescricaoDetalhada(dto.descricaoDetalhada());
        l.setDataHoraInicio(dto.dataHoraInicio());
        l.setDataHoraFim(dto.dataHoraFim());
        l.setStatus(dto.status());
        l.setObservacao(dto.observacao());
        l.setValorIncremento(dto.valorIncremento());
        l.setLanceMinimo(dto.lanceMinimo());
        l.setCategoria(categoria);
        l.setAutor(autor);
        return l;
    }

    public static void updateEntity(Leilao entity, LeilaoRequestDTO dto, Categoria categoria, Pessoa autor) {
        entity.setTitulo(dto.titulo());
        entity.setDescricao(dto.descricao());
        entity.setDescricaoDetalhada(dto.descricaoDetalhada());
        entity.setDataHoraInicio(dto.dataHoraInicio());
        entity.setDataHoraFim(dto.dataHoraFim());
        entity.setStatus(dto.status());
        entity.setObservacao(dto.observacao());
        entity.setValorIncremento(dto.valorIncremento());
        entity.setLanceMinimo(dto.lanceMinimo());
        entity.setCategoria(categoria);
        entity.setAutor(autor);
    }
}
