# API de Transações e Estatísticas

API REST desenvolvida em Java e Spring Boot para registrar transações financeiras e calcular estatísticas sobre os registros realizados em uma janela temporal configurável.

## Funcionalidades

- Registro de transações com validação de valor e data/hora.
- Remoção de todas as transações armazenadas.
- Cálculo de quantidade, soma, média, menor e maior valor das transações dentro do intervalo definido.
- Documentação interativa dos endpoints com OpenAPI e Swagger UI.
- Monitoramento da aplicação com Spring Boot Actuator, Prometheus e Grafana.
- Testes automatizados para os componentes da aplicação.

## Tecnologias

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3
- **Build:** Maven
- **API:** Spring Web e Bean Validation
- **Documentação:** Springdoc OpenAPI / Swagger UI
- **Persistência e mapeamento:** Spring Data, PostgreSQL, H2 e MapStruct
- **Testes:** JUnit 5, Mockito, Spring Boot Test e JaCoCo
- **Monitoramento:** Spring Boot Actuator, Prometheus e Grafana
- **Containerização:** Docker e Docker Compose
- **Utilitários:** Lombok

## Requisitos

- Java 21 ou superior
- Maven
- Docker e Docker Compose (opcionais, para execução em containers)

## Como executar

### Execução local

1. Clone o repositório:

   ```bash
   git clone https://github.com/bispobr/Java-Spring-banco-desafio.git
   ```

2. Acesse a pasta do projeto:

   ```bash
   cd Java-Spring-banco-desafio
   ```

3. Execute a aplicação com Maven:

   ```bash
   ./mvnw spring-boot:run
   ```

   No Windows, utilize `mvnw.cmd spring-boot:run`. Caso o projeto não inclua o Maven Wrapper, execute `mvn spring-boot:run` com o Maven instalado.

### Execução com Docker

Na raiz do projeto, execute:

```bash
docker compose up --build
```

O Docker Compose inicia os serviços definidos na configuração do projeto, incluindo a aplicação e as ferramentas de monitoramento.

## Endpoints

| Método | Endpoint | Descrição |
| --- | --- | --- |
| `POST` | `/transacao` | Registra uma transação após validar os dados enviados. |
| `DELETE` | `/transacao` | Remove todas as transações armazenadas. |
| `GET` | `/estatistica` | Retorna estatísticas das transações dentro da janela temporal configurada. |

### Registrar transação

**Requisição**

```http
POST /transacao
Content-Type: application/json
```

**Corpo**

```json
{
  "valor": 125.50,
  "dataHora": "2025-01-15T14:19:19.437Z"
}
```

- `valor`: valor monetário da transação, obrigatório e maior ou igual a zero.
- `dataHora`: data e hora da transação, obrigatória e não pode estar no futuro.

**Respostas**

- `201 Created`: transação registrada.
- `400 Bad Request`: requisição inválida, como JSON malformado.
- `422 Unprocessable Entity`: dados rejeitados pelas regras de validação.

### Remover transações

**Requisição**

```http
DELETE /transacao
```

Remove todos os registros de transações armazenados.

**Resposta**

- `200 OK`: operação concluída.

### Consultar estatísticas

**Requisição**

```http
GET /estatistica
```

O endpoint aceita o parâmetro opcional `intervalSeconds`, que define a janela temporal em segundos. Quando omitido, o intervalo padrão é de 60 segundos.

Exemplo:

```http
GET /estatistica?intervalSeconds=120
```

A resposta contém os seguintes campos:

| Campo | Descrição |
| --- | --- |
| `count` | Quantidade de transações consideradas. |
| `sum` | Soma dos valores das transações. |
| `avg` | Média dos valores das transações. |
| `min` | Menor valor encontrado. |
| `max` | Maior valor encontrado. |

Quando não há transações na janela consultada, os campos estatísticos retornam zero.

## Documentação e monitoramento

Com a aplicação em execução local, os serviços podem ser acessados pelos endereços abaixo:

| Serviço | URL |
| --- | --- |
| API | `http://localhost:8080` |
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| Actuator Health | `http://localhost:8080/actuator/health` |
| Prometheus (Docker) | `http://localhost:9090` |
| Grafana (Docker) | `http://localhost:3000` |

As credenciais de acesso inicial do Grafana, quando aplicáveis, são definidas na configuração do ambiente Docker.

## Testes

Para executar os testes automatizados com Maven:

```bash
./mvnw test
```

No Windows, utilize `mvnw.cmd test`. O projeto também possui configuração do JaCoCo para coleta de métricas de cobertura.

## Estrutura do projeto

O código é organizado por responsabilidades, incluindo controladores REST, DTOs, tratamento de exceções, mapeadores, modelos, repositórios e serviços. A configuração de containers e monitoramento fica na raiz do projeto.
