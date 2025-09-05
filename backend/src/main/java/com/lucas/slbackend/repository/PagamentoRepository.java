package com.lucas.slbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.slbackend.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {}
