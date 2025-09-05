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

import com.lucas.slbackend.dto.response.LanceResponse;
import com.lucas.slbackend.dto.mapper.LanceMapper;
import com.lucas.slbackend.model.Lance;
import com.lucas.slbackend.service.LanceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lances")
@RequiredArgsConstructor
@Validated
public class LanceController {
  private final LanceService service;

  @GetMapping
  public ResponseEntity<Page<LanceResponse>> list(Pageable pageable) {
    return ResponseEntity.ok(service.list(pageable).map(LanceMapper::toResponse));
  }

  @GetMapping("/{id}")
  public ResponseEntity<LanceResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(LanceMapper.toResponse(service.get(id)));
  }

  @PostMapping
  public ResponseEntity<LanceResponse> create(@Valid @RequestBody Lance body) {
    Lance created = service.create(body);
    return ResponseEntity
        .created(URI.create("/api/lances/" + created.getId()))
        .body(LanceMapper.toResponse(created));
  }

  @PutMapping("/{id}")
  public ResponseEntity<LanceResponse> update(@PathVariable Long id, @Valid @RequestBody Lance body) {
    return ResponseEntity.ok(LanceMapper.toResponse(service.update(id, body)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
