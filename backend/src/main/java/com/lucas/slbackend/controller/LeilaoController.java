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

import com.lucas.slbackend.dto.request.LeilaoRequestDTO;
import com.lucas.slbackend.dto.response.LeilaoResponseDTO;
import com.lucas.slbackend.dto.response.LeilaoResumoDTO;
import com.lucas.slbackend.dto.mapper.LeilaoMapper;
import com.lucas.slbackend.model.Categoria;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.service.LeilaoService;
import com.lucas.slbackend.service.CategoriaService;
import com.lucas.slbackend.service.PessoaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/leiloes")
@RequiredArgsConstructor
@Validated
public class LeilaoController {
  private final LeilaoService service;
  private final CategoriaService categoriaService;
  private final PessoaService pessoaService;

  @GetMapping
  public ResponseEntity<Page<LeilaoResumoDTO>> list(Pageable pageable) {
    return ResponseEntity.ok(service.listDTO(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LeilaoResponseDTO> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.getDTO(id));
  }

  @PostMapping
  public ResponseEntity<LeilaoResponseDTO> create(@Valid @RequestBody LeilaoRequestDTO body) {
    Categoria categoria = categoriaService.get(body.categoriaId());
    Pessoa autor = body.autorId() != null ? pessoaService.get(body.autorId()) : null;
    var entity = LeilaoMapper.toEntity(body, categoria, autor);
    var created = service.create(entity);
    return ResponseEntity.created(URI.create("/api/leiloes/" + created.getId()))
        .body(service.toResponse(created));
  }

  @PutMapping("/{id}")
  public ResponseEntity<LeilaoResponseDTO> update(@PathVariable Long id, @Valid @RequestBody LeilaoRequestDTO body) {
    Categoria categoria = categoriaService.get(body.categoriaId());
    Pessoa autor = body.autorId() != null ? pessoaService.get(body.autorId()) : null;
    var existing = service.get(id);
    LeilaoMapper.updateEntity(existing, body, categoria, autor);
    var updated = service.update(id, existing);
    return ResponseEntity.ok(service.toResponse(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
