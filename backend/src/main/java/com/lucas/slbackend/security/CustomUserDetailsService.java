package com.lucas.slbackend.security;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.slbackend.model.Pessoa;
import com.lucas.slbackend.repository.PessoaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final PessoaRepository pessoaRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Pessoa> opt = pessoaRepository.findByEmailWithPerfis(email);
    Pessoa p = opt.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    Collection<GrantedAuthority> auths = p.getPerfis().stream()
        .filter(pp -> pp.getPerfil() != null && pp.getPerfil().getTipo() != null)
        .map(pp -> new SimpleGrantedAuthority("ROLE_" + pp.getPerfil().getTipo().name()))
        .collect(Collectors.toSet());
    return User.withUsername(p.getEmail())
        .password(p.getSenha())
        .authorities(auths)
        .accountLocked(false)
        .accountExpired(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}
