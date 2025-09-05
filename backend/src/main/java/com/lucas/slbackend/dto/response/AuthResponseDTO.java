package com.lucas.slbackend.dto.response;

import java.util.List;

public record AuthResponseDTO(String token, long expiresAt, List<String> roles, String nome) {

}
