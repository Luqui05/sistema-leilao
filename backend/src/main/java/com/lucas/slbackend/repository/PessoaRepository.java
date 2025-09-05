package com.lucas.slbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lucas.slbackend.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

  Optional<Pessoa> findByEmail(String email);

  @Query("""
      select distinct p
      from Pessoa p
      left join fetch p.perfis pp
      left join fetch pp.perfil pe
      where lower(p.email) = lower(:email)
      """)
  Optional<Pessoa> findByEmailWithPerfis(@Param("email") String email);

  @Query("""
      select distinct p
      from Pessoa p
      left join fetch p.perfis pp
      left join fetch pp.perfil pe
      where p.id = :id
      """)
  Optional<Pessoa> findByIdWithPerfis(@Param("id") Long id);
}
