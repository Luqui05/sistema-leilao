package com.lucas.slbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.lucas.slbackend.dto.request.LoginRequestDTO;
import com.lucas.slbackend.dto.request.ForgotPasswordRequest;
import com.lucas.slbackend.dto.request.ResetPasswordRequest;
import com.lucas.slbackend.dto.request.ChangePasswordRequest;
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

  @PostMapping("/forgot-password")
  public ResponseEntity<Void> forgotPassword(@RequestBody @Validated ForgotPasswordRequest body) {
    authService.issueRecoveryCode(body.email());
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Void> resetPassword(@RequestBody @Validated ResetPasswordRequest body) {
    authService.resetPassword(body.email(), body.codigo(), body.novaSenha());
    return ResponseEntity.noContent().build();
  }

  // Requires authentication; ID should come from token context in a real scenario; simplified version expects an X-User-Id header or adaptation.
  @PostMapping("/change-password")
  public ResponseEntity<Void> changePassword(@RequestHeader("Authorization") Long pessoaId,
      @RequestBody @Validated ChangePasswordRequest body) {
    authService.changePassword(pessoaId, body.senhaAtual(), body.novaSenha());
    return ResponseEntity.noContent().build();
  }
}
