package com.tsantana.models;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record Transacao(
        ObjectId id,
        int idCliente,
        int valor,
        String tipo,
        String descricao,
        LocalDateTime data
) {}
