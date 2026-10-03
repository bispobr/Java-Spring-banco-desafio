# API de Transações e Estatísticas

## Descrição

Esta é uma API REST para registro de transações e cálculo de estatísticas com base nas transações recebidas. O projeto segue boas práticas de desenvolvimento e implementa regras específicas de validação para garantir a integridade dos dados.

---

## Funcionalidades

### **1. Registrar Transação — `POST /transacao`**

A API **somente aceitará** transações que atendam aos seguintes critérios:

- Possuam os campos **valor** e **dataHora** preenchidos.
- A transação **não pode ocorrer no futuro**.
- O valor deve ser **maior ou igual a 0**.

#### **Possíveis respostas:**

- **201 Created** — A transação é válida e foi registrada com sucesso.
- **422 Unprocessable Entity** — A transação foi rejeitada por violar uma ou mais regras de validação.
- **400 Bad Request** — Requisição inválida (ex.: JSON malformado).

---

### **2. Limpar Transações — `DELETE /transacao`**

Remove todas as transações atualmente armazenadas.

#### **Possível resposta:**

- **200 OK** — Todos os dados foram apagados com sucesso.

---

### **3. Estatísticas das Transações — `GET /estatistica`**

Retorna estatísticas calculadas **somente sobre as transações registradas nos últimos 60 segundos**.

As estatísticas incluem:

- **count** → Quantidade de transações
- **sum** → Soma total dos valores
- **avg** → Média dos valores
- **min** → Menor valor registrado
- **max** → Maior valor registrado

#### **Possível resposta:**

- **200 OK** — Retorna um JSON com os campos `count`, `sum`, `avg`, `min` e `max`.
    - Caso não existam transações nos últimos 60 segundos, todos os valores devem ser **0**.

---

## Tecnologias Utilizadas

- **Backend**: Java 21+ com Spring Boot
- **Testes**: JUnit 5, Mockito, Jacoco
- **Documentação**: Swagger UI
- **Monitoramento**: Spring Boot Actuator, Prometheus, Grafana
- **Mapeamento de DTOs**: MapStruct
- **Logging**: Lombok (@Slf4j)
- **Containerização**: Docker
- **Controle de Exceções**: Tratamento de erros com `@RestControllerAdvice`

---

## Requisitos

- Java 21+
- Maven
- Docker(opcional)

---

## Executando o Projeto(Sem Docker)

1. Clone o repositório:

```bash
git https://github.com/bispobr/Java-Spring-banco-desafio.git
```
---

## Executando com docker (Opcional)

1. Gere a Imagem Docker e inicie o containner:

```bash
docker compose up --build
```
---

## Acessando a API

- **Base URL:** http://localhost:8080
- **Documentação (Swagger):**  http://localhost:8080/swagger-ui/index.html#/
- **Health Check (Actuator):**  http://localhost:8080/actuator/health
-  **Grafana (Docker):**  http://localhost:3000
- - usuario: admin
- - senha: admin
- **Prometheus (Docker):**  http://localhost:9090

---

## Endpoints da API
**transação**

1. **Adicionar transação**

```http request
POST /transacao 
Content-Type: application/json
```

**Body**

```http request
{
  "valor": 0,
  "dataHora": "2010-02-05T14:19:19.437Z"
}
```
| Parâmetro | Tipo       | Descrição                           |
|:----------| :--------- | :---------------------------------- |
| `valor`   | `BigDecimal` |   Nome do cliente(**Obrigatório**)
| `dataHora`     | `OffsetDateTime` |   Cpf do cliente(**Obrigatório**) 


3. **Remover todas transações**


```http request
DELETE /transacao
```

**Estatistica**

1. **Gerar estatisticas**

```http request
GET /estatistica
```




