package br.com.itau.desafiobackend.dto;

import java.math.BigDecimal;

public record EstatisticaResponse(
        long count,
        BigDecimal sum,
        BigDecimal avg,
        BigDecimal min,
        BigDecimal max
) {
}
