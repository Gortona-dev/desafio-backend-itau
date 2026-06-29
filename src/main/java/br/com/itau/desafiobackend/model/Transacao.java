package br.com.itau.desafiobackend.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record Transacao(
        BigDecimal valor,
        OffsetDateTime dataHora
) {
}
