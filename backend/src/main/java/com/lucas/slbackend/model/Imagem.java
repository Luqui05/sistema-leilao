package com.lucas.slbackend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "imagem")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHoraCadastro;

    private String nomeImagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leilao_id")
    private Leilao leilao;

    @PrePersist
    void prePersist() {
        if (dataHoraCadastro == null) {
            dataHoraCadastro = LocalDateTime.now();
        }
    }
}
