package com.tsantana.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record SaldoResponse(
        int total,
        @JsonProperty("data_extrato")
        LocalDateTime data,
        int limite) {}
