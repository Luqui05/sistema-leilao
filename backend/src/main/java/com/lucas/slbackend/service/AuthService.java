package com.lucas.slbackend.service;

import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lucas.slbackend.dto.request.LoginRequestDTO;
import com.lucas.slbackend.dto.response.AuthResponseDTO;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.repository.PessoaRepository;
import com.lucas.slbackend.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
  
  private final PessoaRepository pessoaRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthResponseDTO login(LoginRequestDTO body) {
    Pessoa p = pessoaRepository.findByEmailWithPerfis(body.email())
        .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
    if (!passwordEncoder.matches(body.senha(), p.getSenha())) {
      throw new BadCredentialsException("Invalid credentials");
    }
    List<String> roles = p.getPerfis().stream()
        .filter(pp -> pp.getPerfil() != null && pp.getPerfil().getTipo() != null)
        .map(pp -> pp.getPerfil().getTipo().name())
        .distinct()
        .toList();

    String token = jwtUtil.generateToken(p.getEmail(), roles);
    long expiresAt = System.currentTimeMillis() + jwtUtil.getExpirationMillis();
    return new AuthResponseDTO(token, expiresAt, roles, p.getNome());
  }
}
