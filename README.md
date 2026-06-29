# Desafio Itau Backend

Projeto desenvolvido com base no desafio:

[feltex/desafio-itau-backend](https://github.com/feltex/desafio-itau-backend)

Esta API foi criada usando Java com Spring Boot, seguindo o que foi solicitado no desafio: receber transacoes, armazenar tudo em memoria e calcular estatisticas das transacoes realizadas nos ultimos 60 segundos.

Usei Maven para gerenciar as dependencias e o IntelliJ IDEA como IDE de desenvolvimento. Os testes manuais da API foram feitos com Postman.

## O que a API faz

A API possui tres endpoints principais:

- `POST /transacao`: cadastra uma nova transacao.
- `DELETE /transacao`: remove todas as transacoes salvas em memoria.
- `GET /estatistica`: retorna as estatisticas das transacoes dos ultimos 60 segundos.

As estatisticas retornadas sao:

- `count`: quantidade de transacoes.
- `sum`: soma dos valores.
- `avg`: media dos valores.
- `min`: menor valor.
- `max`: maior valor.

## Algumas decisoes do projeto

As transacoes foram armazenadas em memoria, sem banco de dados e sem persistencia externa, conforme solicitado no desafio.

Para representar o valor da transacao, usei `BigDecimal`, porque estamos lidando com dinheiro. Tipos como `double` e `float` podem gerar pequenas imprecisoes em calculos decimais, e isso nao e adequado para valores financeiros.

Para a data e hora da transacao, usei `OffsetDateTime`, pois ele carrega tambem a informacao de offset/fuso horario, deixando a comparacao de datas mais segura.

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
8. Aguarde aparecer no console algo parecido com:

```text
Tomcat started on port 8080
```

Quando isso aparecer, a API estara rodando em:

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

Resposta esperada quando ainda nao existe nenhuma transacao:

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

No Postman, va em `Body`, selecione `raw` e escolha `JSON`.

Exemplo de body:

```json
{
  "valor": 100.50,
  "dataHora": "2026-06-29T15:30:00-03:00"
}
```

Resposta esperada:

```text
201 Created
```

Importante: para a transacao aparecer no resultado de `/estatistica`, a `dataHora` precisa estar dentro dos ultimos 60 segundos.

### Ver estatisticas depois de criar transacoes

Depois de cadastrar uma ou mais transacoes, faca novamente:

```text
GET http://localhost:8080/estatistica
```

Exemplo de resposta:

```json
{
  "count": 1,
  "sum": 100.5,
  "avg": 100.5,
  "min": 100.5,
  "max": 100.5
}
```

### Apagar todas as transacoes

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

## Validacoes

A API valida os seguintes casos:

- Valor da transacao negativo retorna `422 Unprocessable Entity`.
- Data/hora da transacao no futuro retorna `422 Unprocessable Entity`.
- JSON invalido ou campos obrigatorios ausentes retornam `400 Bad Request`.

Exemplo de valor negativo:

```json
{
  "valor": -10.00,
  "dataHora": "2026-06-29T15:30:00-03:00"
}
```

Exemplo de data no futuro:

```json
{
  "valor": 10.00,
  "dataHora": "2099-01-01T10:00:00-03:00"
}
```

## Como rodar os testes

Com Maven instalado, execute:

```bash
mvn test
```

## Melhorias futuras

- Adicionar Swagger/OpenAPI.
- Adicionar mais testes de controller com MockMvc.
- Criar Dockerfile.
- Permitir configurar a janela de estatistica via `application.properties`.
