# Desafio Backend Itau

Este projeto foi desenvolvido para resolver o desafio backend do Itau.

A aplicacao e uma API REST feita com Java e Spring Boot, responsavel por receber transacoes financeiras e retornar estatisticas das transacoes realizadas nos ultimos 60 segundos.

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Bean Validation
- Maven
- IntelliJ IDEA
- Postman

## Objetivo do projeto

Criar uma API REST com os seguintes endpoints:

- `POST /transacao`: registra uma nova transacao.
- `DELETE /transacao`: apaga todas as transacoes salvas em memoria.
- `GET /estatistica`: retorna as estatisticas das transacoes dos ultimos 60 segundos.

As estatisticas retornadas sao:

- `count`: quantidade de transacoes.
- `sum`: soma dos valores.
- `avg`: media dos valores.
- `min`: menor valor.
- `max`: maior valor.

## Regras implementadas

- As transacoes sao armazenadas apenas em memoria.
- Nao foi utilizado banco de dados.
- Nao foi utilizada persistencia externa.
- O valor da transacao nao pode ser negativo.
- A data/hora da transacao nao pode estar no futuro.
- Apenas transacoes dos ultimos 60 segundos entram no calculo da estatistica.
- Foram utilizados status HTTP adequados para cada situacao.

## Por que usar BigDecimal?

O campo `valor` foi implementado com `BigDecimal` porque ele representa valores monetarios com mais seguranca e precisao.

Tipos como `double` e `float` trabalham com ponto flutuante e podem gerar pequenas imprecisoes em calculos decimais. Como o projeto lida com dinheiro, essas imprecisoes nao sao desejadas.

Por isso, `BigDecimal` e a melhor escolha para valores financeiros.

## Por que usar OffsetDateTime?

O campo `dataHora` foi implementado com `OffsetDateTime` porque ele armazena a data e hora junto com o offset do fuso horario.

Isso deixa a API mais segura para trabalhar com horarios vindos de diferentes regioes e evita ambiguidades em comparacoes de tempo.

## Estrutura do projeto

```text
src/main/java/br/com/itau/desafiobackend
├── config
│   └── ClockConfig.java
├── controller
│   ├── EstatisticaController.java
│   └── TransacaoController.java
├── dto
│   ├── ErrorResponse.java
│   ├── EstatisticaResponse.java
│   └── TransacaoRequest.java
├── exception
│   ├── GlobalExceptionHandler.java
│   └── InvalidTransactionException.java
├── model
│   └── Transacao.java
├── service
│   └── TransacaoService.java
└── DesafioBackendApplication.java
```

## Papel de cada camada

### Controller

Camada responsavel por expor os endpoints REST da aplicacao.

Classes:

- `TransacaoController`
- `EstatisticaController`

### Service

Camada responsavel pelas regras de negocio, como validar transacoes, armazenar em memoria e calcular estatisticas.

Classe:

- `TransacaoService`

### DTO

Camada responsavel pelos objetos de entrada e saida da API.

Classes:

- `TransacaoRequest`
- `EstatisticaResponse`
- `ErrorResponse`

### Model

Representa o modelo interno da transacao.

Classe:

- `Transacao`

### Exception

Camada responsavel pelo tratamento de erros da aplicacao.

Classes:

- `InvalidTransactionException`
- `GlobalExceptionHandler`

## Como rodar no IntelliJ

1. Abra o IntelliJ IDEA.
2. Clique em `File > Open`.
3. Selecione a pasta do projeto.
4. Aguarde o IntelliJ importar o projeto Maven.
5. Confirme que o projeto esta usando Java 21.
6. Abra a classe:

```text
src/main/java/br/com/itau/desafiobackend/DesafioBackendApplication.java
```

7. Clique no botao verde ao lado do metodo `main`.
8. Aguarde aparecer no console:

```text
Tomcat started on port 8080
```

Depois disso, a API estara rodando em:

```text
http://localhost:8080
```

## Como testar no Postman

### Consultar estatisticas

Metodo:

```text
GET
```

URL:

```text
http://localhost:8080/estatistica
```

Resposta esperada quando nao existem transacoes:

```json
{
  "count": 0,
  "sum": 0,
  "avg": 0,
  "min": 0,
  "max": 0
}
```

### Criar uma transacao

Metodo:

```text
POST
```

URL:

```text
http://localhost:8080/transacao
```

Body:

```json
{
  "valor": 100.50,
  "dataHora": "2026-06-29T15:30:00-03:00"
}
```

No Postman, selecione:

- `Body`
- `raw`
- `JSON`

Resposta esperada:

```text
201 Created
```

Importante: para aparecer na estatistica, a transacao precisa ter uma data/hora dentro dos ultimos 60 segundos.

### Limpar transacoes

Metodo:

```text
DELETE
```

URL:

```text
http://localhost:8080/transacao
```

Resposta esperada:

```text
200 OK
```

## Exemplos de erros

### Valor negativo

Request:

```json
{
  "valor": -10.00,
  "dataHora": "2026-06-29T15:30:00-03:00"
}
```

Resposta esperada:

```text
422 Unprocessable Entity
```

### Data no futuro

Request:

```json
{
  "valor": 10.00,
  "dataHora": "2099-01-01T10:00:00-03:00"
}
```

Resposta esperada:

```text
422 Unprocessable Entity
```

### JSON invalido ou campo ausente

Request:

```json
{
  "valor": 10.00
}
```

Resposta esperada:

```text
400 Bad Request
```

## Como rodar os testes

Com Maven instalado, execute:

```bash
mvn test
```

## Melhorias opcionais

- Adicionar Swagger/OpenAPI para documentar os endpoints.
- Adicionar mais testes de controller com MockMvc.
- Configurar a janela de estatistica via `application.properties`.
- Adicionar logs para facilitar monitoramento.
- Criar Dockerfile para executar a aplicacao em container.
