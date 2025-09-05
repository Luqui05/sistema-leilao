package com.lucas.slbackend.controller;

import java.net.URI;

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

import com.lucas.slbackend.dto.request.PessoaPerfilRequestDTO;
import com.lucas.slbackend.dto.response.PessoaPerfilResponseDTO;
import com.lucas.slbackend.service.PessoaPerfilService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pessoas-perfis")
@RequiredArgsConstructor
@Validated
public class PessoaPerfilController {
  private final PessoaPerfilService service;

  @GetMapping
  public ResponseEntity<Page<PessoaPerfilResponseDTO>> list(Pageable pageable) {
    return ResponseEntity.ok(service.listDTO(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PessoaPerfilResponseDTO> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.getDTO(id));
  }

  @PostMapping
  public ResponseEntity<PessoaPerfilResponseDTO> create(@Valid @RequestBody PessoaPerfilRequestDTO body) {
    PessoaPerfilResponseDTO created = service.create(body);
    return ResponseEntity.created(URI.create("/api/pessoas-perfis/" + created.id())).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PessoaPerfilResponseDTO> update(@PathVariable Long id, @Valid @RequestBody PessoaPerfilRequestDTO body) {
    return ResponseEntity.ok(service.update(id, body));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}