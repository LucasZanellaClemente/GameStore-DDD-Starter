package br.com.fiap.gamestore.dto;

import jakarta.validation.constraints.*;

public record OrderRequest(
        @NotNull @Positive Long customerId,
        @NotNull @Positive Long gameId,
        @NotNull @Positive Integer quantidade
) {}
