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

import com.lucas.slbackend.dto.request.FeedbackRequestDTO;
import com.lucas.slbackend.dto.response.FeedbackResponseDTO;
import com.lucas.slbackend.service.FeedbackService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
@Validated
public class FeedbackController {
  private final FeedbackService service;

  @GetMapping
  public ResponseEntity<Page<FeedbackResponseDTO>> list(Pageable pageable) {
    return ResponseEntity.ok(service.list(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<FeedbackResponseDTO> get(@PathVariable Long id) {
    return ResponseEntity.ok(service.get(id));
  }

  @PostMapping
  public ResponseEntity<FeedbackResponseDTO> create(@Valid @RequestBody FeedbackRequestDTO body) {
    FeedbackResponseDTO created = service.create(body);
    return ResponseEntity.created(URI.create("/api/feedbacks/" + created.id())).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<FeedbackResponseDTO> update(@PathVariable Long id, @Valid @RequestBody FeedbackRequestDTO body) {
    return ResponseEntity.ok(service.update(id, body));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
