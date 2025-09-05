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

import com.lucas.slbackend.dto.response.LeilaoResponseDTO;
import com.lucas.slbackend.dto.response.LeilaoResumoDTO;
import com.lucas.slbackend.model.Leilao;
import com.lucas.slbackend.service.LeilaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/leiloes")
@RequiredArgsConstructor
@Validated
public class LeilaoController {
  private final LeilaoService service;

  @GetMapping
  public ResponseEntity<Page<LeilaoResumoDTO>> list(Pageable pageable) {
    return ResponseEntity.ok(service.listDTO(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LeilaoResponseDTO> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.getDTO(id));
  }

  @PostMapping
  public ResponseEntity<LeilaoResponseDTO> create(@Valid @RequestBody Leilao body) {
    Leilao created = service.create(body);
    return ResponseEntity.created(URI.create("/api/leiloes/" + created.getId()))
        .body(service.toResponse(created));
  }

  @PutMapping("/{id}")
  public ResponseEntity<LeilaoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody Leilao body) {
    Leilao updated = service.update(id, body);
    return ResponseEntity.ok(service.toResponse(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
