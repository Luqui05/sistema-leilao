package com.lucas.slbackend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.slbackend.dto.response.PessoaComPerfisDTO;
import com.lucas.slbackend.dto.response.PessoaResponseDTO;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.service.PessoaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
@Validated
public class PessoaController {
  private final PessoaService service;

  @GetMapping
  public ResponseEntity<Page<PessoaResponseDTO>> list(Pageable pageable) {
    return ResponseEntity.ok(service.listDTO(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PessoaResponseDTO> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.getResponse(id));
  }

  // quando precisar trazer perfis da pessoa
  @GetMapping("/{id}/com-perfis")
  public ResponseEntity<PessoaComPerfisDTO> getComPerfis(@PathVariable Long id) {
    return ResponseEntity.ok(service.getWithPerfisDTO(id));
  }

  @PostMapping
  public ResponseEntity<PessoaResponseDTO> create(@Valid @RequestBody Pessoa body) {
    Pessoa created = service.create(body);
    return ResponseEntity.created(URI.create("/api/pessoas/" + created.getId()))
        .body(service.toResponse(created));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PessoaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody Pessoa body) {
    Pessoa updated = service.update(id, body);
    return ResponseEntity.ok(service.toResponse(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}