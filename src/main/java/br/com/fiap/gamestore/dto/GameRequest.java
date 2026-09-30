package br.com.fiap.gamestore.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record GameRequest(
        @NotBlank @Size(max = 120) String titulo,
        @NotBlank @Size(max = 60) String genero,
        @NotNull @Positive BigDecimal preco,
        @NotBlank @Size(max = 40) String plataforma,
        @NotNull @Min(0) Integer estoque
) {}
