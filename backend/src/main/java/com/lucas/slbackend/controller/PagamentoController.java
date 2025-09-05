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

import com.lucas.slbackend.model.Pagamento;
import com.lucas.slbackend.service.PagamentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagamentos")
@RequiredArgsConstructor
@Validated
public class PagamentoController {
  private final PagamentoService service;

  @GetMapping
  public ResponseEntity<Page<Pagamento>> list(Pageable pageable) {
    return ResponseEntity.ok(service.list(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Pagamento> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.get(id));
  }

  @PostMapping
  public ResponseEntity<Pagamento> create(@Valid @RequestBody Pagamento body) {
    Pagamento created = service.create(body);
    return ResponseEntity.created(URI.create("/api/pagamentos/" + created.getId())).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Pagamento> update(@PathVariable Long id, @Valid @RequestBody Pagamento body) {
    return ResponseEntity.ok(service.update(id, body));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
