package com.tsantana.dtos;

public record PostTransacaoRequest(
        String valor,
        String tipo,
        String descricao
) {}
