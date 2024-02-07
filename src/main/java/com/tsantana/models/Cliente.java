package com.tsantana.models;

import org.bson.codecs.pojo.annotations.BsonId;

public record Cliente(
        @BsonId
        int id,
        int limite
) {}
