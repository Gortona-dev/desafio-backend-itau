package br.com.itau.desafiobackend.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoRequest(
        @NotNull BigDecimal valor,
        @NotNull OffsetDateTime dataHora
) {
}
