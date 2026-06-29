package br.com.ortona.spring_boot_itau.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record Transacao(
        BigDecimal valor,
        OffsetDateTime dataHora
) {
}
