package com.lucas.slbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.lucas.slbackend.dto.request.LoginRequestDTO;
import com.lucas.slbackend.dto.response.AuthResponseDTO;
import com.lucas.slbackend.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO body) {
    return ResponseEntity.ok(authService.login(body));
  }
}
