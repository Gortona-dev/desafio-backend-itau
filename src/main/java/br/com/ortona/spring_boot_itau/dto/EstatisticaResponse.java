package br.com.ortona.spring_boot_itau.dto;

import java.math.BigDecimal;

public record EstatisticaResponse(
        long count,
        BigDecimal sum,
        BigDecimal avg,
        BigDecimal min,
        BigDecimal max
) {
}
