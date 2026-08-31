<div align="center">

# Desafio Backend Itau

API REST em Java com Spring Boot para registrar transacoes em memoria e calcular estatisticas dos ultimos 60 segundos.

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![API REST](https://img.shields.io/badge/API-REST-60A5FA?style=for-the-badge)

</div>

---

## Objetivo

Este projeto foi desenvolvido como solucao para o desafio backend do Itau. A API recebe transacoes financeiras, armazena os dados em memoria e retorna estatisticas calculadas apenas com as transacoes realizadas nos ultimos 60 segundos.

O foco do projeto e praticar conceitos importantes de back-end:

- criacao de API REST;
- validacao de entrada;
- tratamento global de excecoes;
- uso correto de tipos para valores monetarios;
- separacao entre controller, service, DTO e model;
- testes automatizados da regra de negocio.

## Funcionalidades

- Criar uma transacao.
- Remover todas as transacoes em memoria.
- Consultar estatisticas em tempo real.
- Validar transacoes com valor negativo.
- Validar transacoes com data futura.
- Retornar erros padronizados para requisicoes invalidas.
- Calcular `count`, `sum`, `avg`, `min` e `max`.

## Tecnologias

| Tecnologia | Uso |
| --- | --- |
| Java 21 | Linguagem principal |
| Spring Boot | Estrutura da API |
| Spring Web | Criacao dos endpoints REST |
| Spring Validation | Validacao dos dados recebidos |
| Maven | Gerenciamento de dependencias e build |
| JUnit | Testes automatizados |

## Endpoints

| Metodo | Rota | Descricao |
| --- | --- | --- |
| `POST` | `/transacao` | Registra uma nova transacao |
| `DELETE` | `/transacao` | Remove todas as transacoes |
| `GET` | `/estatistica` | Retorna estatisticas dos ultimos 60 segundos |

## Como Rodar Localmente

### Requisitos

- Java 21
- Maven instalado ou uso do Maven Wrapper, caso exista no ambiente
- Postman, Insomnia ou outro cliente HTTP para testar

### 1. Clonar o repositorio

```bash
git clone https://github.com/Gortona-dev/desafio-backend-itau.git
cd desafio-backend-itau
```

### 2. Instalar dependencias e rodar

Com Maven instalado:

```bash
mvn spring-boot:run
```

A API ficara disponivel em:

```text
http://localhost:8080
```

## Como Testar a API

### Criar transacao

```http
POST http://localhost:8080/transacao
Content-Type: application/json
```

```json
{
  "valor": 100.50,
  "dataHora": "2026-08-30T10:15:30-03:00"
}
```

Resposta esperada:

```text
201 Created
```

Importante: para a transacao entrar no calculo de estatisticas, a data deve estar dentro dos ultimos 60 segundos.

### Consultar estatisticas

```http
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

### Limpar transacoes

```http
DELETE http://localhost:8080/transacao
```

Resposta esperada:

```text
200 OK
```

## Validacoes

| Caso | Status esperado |
| --- | --- |
| Valor negativo | `422 Unprocessable Entity` |
| Data no futuro | `422 Unprocessable Entity` |
| JSON invalido | `400 Bad Request` |
| Campos obrigatorios ausentes | `400 Bad Request` |

## Rodar Testes

```bash
mvn test
```

## Estrutura

```text
src/main/java/br/com/ortona/spring_boot_itau/
+-- config/
+-- controller/
+-- dto/
+-- exception/
+-- model/
+-- service/
+-- DesafioBackendApplication.java
```

## Autor

Desenvolvido por [Gabriel Ortona](https://github.com/Gortona-dev).

