package com.tsantana.models;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.LinkedList;

public record Cliente(
        @BsonId
        int id,
        int limite,
        int saldo,
        LinkedList<Transacao> transacoes
) {}
