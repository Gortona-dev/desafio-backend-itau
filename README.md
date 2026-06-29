# Desafio Backend Itau

API REST em Java com Spring Boot para registrar transacoes em memoria e calcular estatisticas das transacoes realizadas nos ultimos 60 segundos.

## Estrutura

```text
src/main/java/br/com/itau/desafiobackend
├── DesafioBackendApplication.java
├── config
│   └── ClockConfig.java
├── controller
│   ├── EstatisticaController.java
│   └── TransacaoController.java
├── dto
│   ├── EstatisticaResponse.java
│   ├── ErrorResponse.java
│   └── TransacaoRequest.java
├── exception
│   ├── GlobalExceptionHandler.java
│   └── InvalidTransactionException.java
├── model
│   └── Transacao.java
└── service
    └── TransacaoService.java
```

## Como executar

```bash
mvn spring-boot:run
```

## Como testar

Criar transacao:

```bash
curl -i -X POST http://localhost:8080/transacao \
  -H "Content-Type: application/json" \
  -d '{"valor": 123.45, "dataHora": "2026-06-29T10:00:00-03:00"}'
```

Consultar estatisticas:

```bash
curl -i http://localhost:8080/estatistica
```

Limpar transacoes:

```bash
curl -i -X DELETE http://localhost:8080/transacao
```
