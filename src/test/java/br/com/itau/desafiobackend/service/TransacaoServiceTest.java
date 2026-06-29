package br.com.itau.desafiobackend.service;

import br.com.itau.desafiobackend.dto.EstatisticaResponse;
import br.com.itau.desafiobackend.dto.TransacaoRequest;
import br.com.itau.desafiobackend.exception.InvalidTransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransacaoServiceTest {

    private static final Clock CLOCK = Clock.fixed(Instant.parse("2026-06-29T12:00:00Z"), ZoneOffset.UTC);

    private TransacaoService service;

    @BeforeEach
    void setUp() {
        service = new TransacaoService(CLOCK);
    }

    @Test
    void deveCriarTransacaoValidaECalcularEstatisticas() {
        service.criar(new TransacaoRequest(BigDecimal.valueOf(10), now().minusSeconds(10)));
        service.criar(new TransacaoRequest(BigDecimal.valueOf(20), now().minusSeconds(20)));

        EstatisticaResponse response = service.calcularEstatisticas();

        assertThat(response.count()).isEqualTo(2);
        assertThat(response.sum()).isEqualByComparingTo("30");
        assertThat(response.avg()).isEqualByComparingTo("15");
        assertThat(response.min()).isEqualByComparingTo("10");
        assertThat(response.max()).isEqualByComparingTo("20");
    }

    @Test
    void deveIgnorarTransacoesForaDaJanelaDeSessentaSegundos() {
        service.criar(new TransacaoRequest(BigDecimal.valueOf(10), now().minusSeconds(10)));
        service.criar(new TransacaoRequest(BigDecimal.valueOf(50), now().minusSeconds(61)));

        EstatisticaResponse response = service.calcularEstatisticas();

        assertThat(response.count()).isEqualTo(1);
        assertThat(response.sum()).isEqualByComparingTo("10");
    }

    @Test
    void deveRetornarZerosQuandoNaoExistiremTransacoesNaJanela() {
        EstatisticaResponse response = service.calcularEstatisticas();

        assertThat(response.count()).isZero();
        assertThat(response.sum()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(response.avg()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(response.min()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(response.max()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void deveLimparTransacoes() {
        service.criar(new TransacaoRequest(BigDecimal.valueOf(10), now().minusSeconds(10)));

        service.limpar();

        assertThat(service.calcularEstatisticas().count()).isZero();
    }

    @Test
    void deveRejeitarValorNegativo() {
        TransacaoRequest request = new TransacaoRequest(BigDecimal.valueOf(-1), now());

        assertThatThrownBy(() -> service.criar(request))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessageContaining("nao pode ser negativo");
    }

    @Test
    void deveRejeitarDataHoraNoFuturo() {
        TransacaoRequest request = new TransacaoRequest(BigDecimal.ONE, now().plusSeconds(1));

        assertThatThrownBy(() -> service.criar(request))
                .isInstanceOf(InvalidTransactionException.class)
                .hasMessageContaining("nao pode estar no futuro");
    }

    private OffsetDateTime now() {
        return OffsetDateTime.now(CLOCK);
    }
}
