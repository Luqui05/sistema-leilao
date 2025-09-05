package com.lucas.slbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.slbackend.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

  @Query("select distinct c from Categoria c left join fetch c.leiloes where c.id = :id")
  Optional<Categoria> findByIdWithLeiloes(@Param("id") Long id);
}
