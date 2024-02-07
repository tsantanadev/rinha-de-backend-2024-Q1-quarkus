package com.tsantana.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransacaoDetailResponse(
        int valor,
        String tipo,
        String descricao,
        @JsonProperty("realizada_em")
        String data
) {}
