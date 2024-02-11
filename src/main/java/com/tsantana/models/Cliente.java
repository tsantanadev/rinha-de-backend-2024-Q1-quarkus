package com.tsantana.models;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.LinkedList;

@MongoEntity(collection = "cliente")
public record Cliente(
        @BsonId
        int id,
        int limite,
        int saldo,
        LinkedList<Transacao> transacoes
) {}
