package com.lucas.slbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.slbackend.model.Lance;

public interface LanceRepository extends JpaRepository<Lance, Long> {}
