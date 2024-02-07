package com.tsantana.models;

import java.time.LocalDateTime;

public record Transacao (

        int valor,
        String tipo,
        String descricao,
        LocalDateTime data
) {}
