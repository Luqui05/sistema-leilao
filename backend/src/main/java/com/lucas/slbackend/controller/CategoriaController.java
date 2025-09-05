package com.lucas.slbackend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.lucas.slbackend.dto.request.CategoriaRequestDTO;
import com.lucas.slbackend.dto.response.CategoriaComLeiloesDTO;
import com.lucas.slbackend.dto.response.CategoriaResponseDTO;
import com.lucas.slbackend.model.Categoria;
import com.lucas.slbackend.service.CategoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Validated
public class CategoriaController {
  private final CategoriaService service;

  @GetMapping
  public ResponseEntity<Page<CategoriaResponseDTO>> list(Pageable pageable) {
    return ResponseEntity.ok(service.listDTO(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoriaResponseDTO> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.getResponse(id));
  }

  // quando precisar trazer 'leiloes', usa fetch join e DTO
  @GetMapping("/{id}/com-leiloes")
  public ResponseEntity<CategoriaComLeiloesDTO> getComLeiloes(@PathVariable Long id) {
    return ResponseEntity.ok(service.getWithLeiloesDTO(id));
  }

  @PostMapping
  public ResponseEntity<CategoriaResponseDTO> create(@Valid @RequestBody CategoriaRequestDTO body) {
    Categoria saved = service.create(body);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new CategoriaResponseDTO(saved.getId(), saved.getNome()));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Categoria> update(@PathVariable Long id, @Valid @RequestBody Categoria body) {
    return ResponseEntity.ok(service.update(id, body));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
