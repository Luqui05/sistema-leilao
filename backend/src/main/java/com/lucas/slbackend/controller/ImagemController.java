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

import com.lucas.slbackend.dto.request.ImagemRequestDTO;
import com.lucas.slbackend.dto.response.ImagemResponse;
import com.lucas.slbackend.dto.mapper.ImagemMapper;
import com.lucas.slbackend.model.Imagem;
import com.lucas.slbackend.service.ImagemService;
import com.lucas.slbackend.service.LeilaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/imagens")
@RequiredArgsConstructor
@Validated
public class ImagemController {
  private final ImagemService service;
  private final LeilaoService leilaoService;

  @GetMapping
  public ResponseEntity<Page<ImagemResponse>> list(Pageable pageable) {
    return ResponseEntity.ok(service.list(pageable).map(ImagemMapper::toResponse));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ImagemResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(ImagemMapper.toResponse(service.get(id)));
  }

  @PostMapping
  public ResponseEntity<ImagemResponse> create(@Valid @RequestBody ImagemRequestDTO body) {
    var leilao = leilaoService.get(body.leilaoId());
    var entity = ImagemMapper.toEntity(body, leilao);
    var created = service.create(entity);
    return ResponseEntity
        .created(URI.create("/api/imagens/" + created.getId()))
        .body(ImagemMapper.toResponse(created));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ImagemResponse> update(@PathVariable Long id, @Valid @RequestBody ImagemRequestDTO body) {
    var leilao = leilaoService.get(body.leilaoId());
    var existing = service.get(id);
    ImagemMapper.updateEntity(existing, body, leilao);
    var updated = service.update(id, existing);
    return ResponseEntity.ok(ImagemMapper.toResponse(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
