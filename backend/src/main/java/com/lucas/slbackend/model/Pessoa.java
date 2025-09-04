package com.lucas.slbackend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 6)
    @Column(nullable = false)
    private String senha;

    private String codigoValidacao;

    @PastOrPresent
    private LocalDateTime validadeCodigoValidacao;

    private Boolean ativo;

    @Lob
    private byte[] fotoPerfil;

    // Relacionamentos
    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY)
    private List<PessoaPerfil> perfis = new ArrayList<>();

    @OneToMany(mappedBy = "criador", fetch = FetchType.LAZY)
    private List<Categoria> categorias = new ArrayList<>();

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<Leilao> leiloes = new ArrayList<>();

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<Lance> lances = new ArrayList<>();

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<Feedback> feedbacksEscritos = new ArrayList<>();

    @OneToMany(mappedBy = "destinatario", fetch = FetchType.LAZY)
    private List<Feedback> feedbacksRecebidos = new ArrayList<>();
}
