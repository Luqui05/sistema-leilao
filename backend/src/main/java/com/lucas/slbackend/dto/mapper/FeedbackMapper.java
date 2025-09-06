package com.lucas.slbackend.dto.mapper;

import com.lucas.slbackend.dto.request.FeedbackRequestDTO;
import com.lucas.slbackend.dto.response.FeedbackResponseDTO;
import com.lucas.slbackend.model.Feedback;
import com.lucas.slbackend.model.Pessoa;

public class FeedbackMapper {

    public static FeedbackResponseDTO toResponseDTO(Feedback feedback) {
        return new FeedbackResponseDTO(
            feedback.getId(),
            feedback.getComentario(),
            feedback.getNota(),
            feedback.getDataHora(),
            feedback.getAutor() != null ? feedback.getAutor().getId() : null,
            feedback.getDestinatario() != null ? feedback.getDestinatario().getId() : null
        );
    }

    public static Feedback toEntity(FeedbackRequestDTO dto, Pessoa autor, Pessoa destinatario) {
        Feedback entity = new Feedback();
        entity.setComentario(dto.comentario());
        entity.setNota(dto.nota());
        entity.setDataHora(dto.dataHora());
        entity.setAutor(autor);
        entity.setDestinatario(destinatario);
        return entity;
    }

    public static void updateEntity(Feedback entity, FeedbackRequestDTO dto, Pessoa autor, Pessoa destinatario) {
        entity.setComentario(dto.comentario());
        entity.setNota(dto.nota());
        entity.setDataHora(dto.dataHora());
        entity.setAutor(autor);
        entity.setDestinatario(destinatario);
    }
}