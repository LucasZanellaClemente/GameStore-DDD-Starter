package br.com.fiap.gamestore.dto;

import jakarta.validation.constraints.*;

public record CustomerRequest(
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Email @Size(max = 160) String email
) {}
