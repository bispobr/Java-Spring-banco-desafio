# API de Transações Bancárias

API REST desenvolvida em Java com Spring Boot para praticar a implementação de uma aplicação relacionada ao processamento de transações bancárias.

O projeto utiliza validação de dados, documentação OpenAPI/Swagger e monitoramento por meio do Spring Boot Actuator.

## Funcionalidades

- API REST para operações relacionadas a transações
- Validação dos dados de entrada
- Documentação interativa com Swagger/OpenAPI
- Monitoramento da aplicação com Spring Boot Actuator
- Testes automatizados com Spring Boot Test

## Tecnologias

- Java 21
- Spring Boot 3.4.4
- Spring Web
- Spring Boot Validation
- Spring Boot Actuator
- Springdoc OpenAPI
- Lombok
- Maven
- JUnit / Spring Boot Test

As versões acima foram identificadas no `pom.xml` atual do projeto. fileciteturn158file0L2-L6

## Requisitos

- Java 21+
- Maven

O projeto também possui Maven Wrapper (`mvnw` e `mvnw.cmd`), permitindo executar os comandos Maven sem depender de uma instalação global do Maven. fileciteturn157file0L2-L2

## Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/bispobr/Java-Spring-banco-desafio.git
cd Java-Spring-banco-desafio
```

Execute utilizando o Maven Wrapper:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação Spring Boot utiliza, por padrão, a porta `8080`, salvo configuração diferente no projeto.

## Swagger / OpenAPI

Com a aplicação em execução, a documentação interativa pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

A especificação OpenAPI pode ser consultada em:

```text
http://localhost:8080/v3/api-docs
```

## Actuator

O Spring Boot Actuator está incluído no projeto para disponibilizar recursos de monitoramento e gerenciamento da aplicação.

Endpoint de saúde padrão:

```text
http://localhost:8080/actuator/health
```

## Validação

A aplicação utiliza `spring-boot-starter-validation` para validação dos dados recebidos pela API.

As regras específicas de validação devem ser consultadas diretamente nas classes responsáveis pelos contratos de entrada.

## Testes

Execute os testes automatizados com:

```bash
./mvnw test
```

No Windows:

```bash
mvnw.cmd test
```

## Estrutura

O projeto utiliza Maven e Spring Boot. O arquivo `pom.xml` centraliza as dependências e configurações de build.

A estrutura de código está organizada dentro do diretório `src`, seguindo a estrutura convencional de aplicações Spring Boot. fileciteturn157file0L2-L2

## Build

Para gerar o artefato da aplicação:

```bash
./mvnw clean package
```

No Windows:

```bash
mvnw.cmd clean package
```

## Fluxo simplificado

```text
Cliente
   │
   ▼
API REST
   │
   ▼
Validação
   │
   ▼
Processamento da transação
   │
   ▼
Resposta HTTP
```

## Status

Projeto desenvolvido para praticar a construção de APIs REST com Java e Spring Boot, com foco em transações bancárias, validação, documentação e monitoramento.
