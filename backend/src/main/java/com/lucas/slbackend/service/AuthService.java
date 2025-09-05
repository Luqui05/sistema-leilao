package com.lucas.slbackend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.dto.request.LoginRequestDTO;
import com.lucas.slbackend.dto.response.AuthResponseDTO;
import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.repository.PessoaRepository;
import com.lucas.slbackend.security.JwtUtil;
import com.lucas.slbackend.util.PasswordUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  
  private final PessoaRepository pessoaRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final MailService mailService;

  @Value("${recovery.expiration-minutes:${RECOVERY_EXP_MINUTES:15}}")
  private int recoveryExpirationMinutes;

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

  /** Issues a recovery code if email exists. Always returns silently (no account existence leak). */
  @Transactional
  public void issueRecoveryCode(String rawEmail) {
    final String email = rawEmail == null ? null : rawEmail.trim().toLowerCase();
    if (email == null || email.isBlank()) return;
    pessoaRepository.findByEmail(email).ifPresent(p -> {
      p.setCodigoValidacao(PasswordUtil.generateRecoveryCode());
      p.setValidadeCodigoValidacao(LocalDateTime.now().plusMinutes(Math.max(recoveryExpirationMinutes, 1)));
      mailService.send(
        p.getEmail(),
        "SL - Código de recuperação de senha",
        buildRecoveryHtml(p.getNome(), p.getCodigoValidacao())
      );
    });
  }

  @Transactional
  public void resetPassword(String rawEmail, String rawCodigo, String novaSenha) {
    final String email = rawEmail == null ? null : rawEmail.trim().toLowerCase();
    final String codigo = rawCodigo == null ? null : rawCodigo.trim();

    Pessoa p = (email == null ? null : pessoaRepository.findByEmail(email).orElse(null));
    // Sempre comportamento "genérico" se não achar (silencioso), mas se achar valida rigorosamente
    if (p == null) return;

    if (p.getCodigoValidacao() == null || p.getValidadeCodigoValidacao() == null)
      throw new IllegalArgumentException("Código inválido ou expirado");
    if (codigo == null || !p.getCodigoValidacao().equalsIgnoreCase(codigo))
      throw new IllegalArgumentException("Código inválido ou expirado");
    if (LocalDateTime.now().isAfter(p.getValidadeCodigoValidacao()))
      throw new IllegalArgumentException("Código inválido ou expirado");

    if (!PasswordUtil.isStrongPassword(novaSenha))
      throw new IllegalArgumentException("Senha fraca: mínimo 6, maiúscula, minúscula, dígito e especial");

    p.setSenha(passwordEncoder.encode(novaSenha));
    p.setCodigoValidacao(null);            // invalida uso futuro
    p.setValidadeCodigoValidacao(null);

    // Persistência explícita garante update mesmo se entidade estiver detached
    pessoaRepository.save(p);
    log.debug("Senha redefinida para usuario id={}", p.getId());
  }

  @Transactional
  public void changePassword(Long pessoaId, String senhaAtual, String novaSenha) {
    Pessoa p = pessoaRepository.findById(pessoaId)
      .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    if (!passwordEncoder.matches(senhaAtual, p.getSenha()))
      throw new IllegalArgumentException("Senha atual incorreta");
    if (!PasswordUtil.isStrongPassword(novaSenha))
      throw new IllegalArgumentException("Senha fraca: mínimo 6, maiúscula, minúscula, dígito e especial");
    p.setSenha(passwordEncoder.encode(novaSenha));
  }

  private String buildRecoveryHtml(String nome, String code) {
    return "<html><body style='font-family:Arial,sans-serif;color:#111'>" +
        "<h2>Recuperação de senha</h2>" +
        "<p>Olá, " + (nome == null ? "usuário" : nome.split(" ")[0]) + "</p>" +
        "<p>Use o código abaixo para redefinir sua senha (válido por " + recoveryExpirationMinutes + " minutos):</p>" +
        "<div style='font-size:26px;font-weight:bold;letter-spacing:4px;margin:16px 0'>" + code + "</div>" +
        "<p>Se você não solicitou, ignore este email.</p>" +
        "<p style='font-size:12px;color:#555'>Mensagem automática - não responda.</p>" +
        "</body></html>";
  }
}
