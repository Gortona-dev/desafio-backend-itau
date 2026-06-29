package br.com.itau.desafiobackend.service;

import br.com.itau.desafiobackend.dto.EstatisticaResponse;
import br.com.itau.desafiobackend.dto.TransacaoRequest;
import br.com.itau.desafiobackend.exception.InvalidTransactionException;
import br.com.itau.desafiobackend.model.Transacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class TransacaoService {

    private static final long JANELA_ESTATISTICA_EM_SEGUNDOS = 60;

    private final Queue<Transacao> transacoes = new ConcurrentLinkedQueue<>();
    private final Clock clock;

    public TransacaoService(Clock clock) {
        this.clock = clock;
    }

    public void criar(TransacaoRequest request) {
        validar(request);
        transacoes.add(new Transacao(request.valor(), request.dataHora()));
    }

    public void limpar() {
        transacoes.clear();
    }

    public EstatisticaResponse calcularEstatisticas() {
        OffsetDateTime limite = OffsetDateTime.now(clock).minusSeconds(JANELA_ESTATISTICA_EM_SEGUNDOS);

        List<BigDecimal> valores = transacoes.stream()
                .filter(transacao -> !transacao.dataHora().isBefore(limite))
                .map(Transacao::valor)
                .toList();

        if (valores.isEmpty()) {
            return new EstatisticaResponse(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        BigDecimal sum = valores.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal avg = sum.divide(BigDecimal.valueOf(valores.size()), MathContext.DECIMAL64);
        BigDecimal min = valores.stream()
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        BigDecimal max = valores.stream()
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        return new EstatisticaResponse(valores.size(), sum, avg, min, max);
    }

    private void validar(TransacaoRequest request) {
        if (request.valor().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidTransactionException("O valor da transacao nao pode ser negativo.");
        }

        if (request.dataHora().isAfter(OffsetDateTime.now(clock))) {
            throw new InvalidTransactionException("A data/hora da transacao nao pode estar no futuro.");
        }
    }
}
