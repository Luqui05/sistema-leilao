package com.lucas.slbackend.model;

import java.util.ArrayList;
import java.util.List;

import com.lucas.slbackend.enums.TipoPerfil;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPerfil tipo;

    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
    private List<PessoaPerfil> pessoas = new ArrayList<>();
}
