package com.tsantana.dtos;

import jakarta.validation.constraints.*;

public record PostTransacaoRequest(
        @PositiveOrZero
        int valor,

        @NotBlank
        String tipo,
        @Size(min = 1, max = 10)
        String descricao
) {}
