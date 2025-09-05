package com.lucas.slbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.slbackend.enums.TipoPerfil;
import com.lucas.slbackend.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
  Optional<Perfil> findByTipo(TipoPerfil tipo);
}
