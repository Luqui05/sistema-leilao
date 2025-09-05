package com.lucas.slbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.slbackend.model.Leilao;

public interface LeilaoRepository extends JpaRepository<Leilao, Long> {}
