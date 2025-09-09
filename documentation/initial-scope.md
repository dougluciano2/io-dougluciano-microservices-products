# Arquitetura Inicial: Serviço de Catálogo de Produtos

## 1. Visão Geral

Este documento descreve a arquitetura inicial para o **Serviço de Catálogo de Produtos**, o primeiro microserviço do ecossistema de E-commerce.

A principal responsabilidade deste serviço é gerenciar todas as informações relacionadas a produtos, incluindo seus dados básicos, preços, categorias e informações de estoque. Ele atuará como a fonte da verdade para qualquer outro serviço que precise consultar dados de produtos.

### Princípios Arquiteturais
* **Database per Service:** Este serviço terá seu próprio banco de dados PostgreSQL, isolado dos outros serviços.
* **API RESTful:** A comunicação com o serviço será feita através de uma API REST bem definida.
* **Stateless:** O serviço será sem estado (stateless), não guardando informações de sessão do cliente.

## 2. Stack Tecnológica

* **Linguagem:** Java 21+
* **Framework:** Spring Boot 3.5.5
* **Build Tool:** Maven
* **Banco de Dados:** PostgreSQL
* **Persistência:** Spring Data JPA / Hibernate
* **Migrações de DB:** Flyway
* **Documentação da API:** **[ATUALIZADO]** Springdoc OpenAPI (Swagger UI) - Versão `2.5.0`
* **Containerização:** Docker / Docker Compose

## 3. Estrutura de Pacotes

A estrutura de pacotes será organizada por funcionalidade/camada para manter o código limpo e coeso.

```
com.meuecommerce.catalog
├── api
│   ├── controller      // Controladores REST (a "borda" da API)
│   ├── dto             // Data Transfer Objects para requisições e respostas
│   └── view            // Interfaces marcadoras para o @JsonView
├── domain
│   ├── model           // Entidades JPA (@Entity)
│   └── repository      // Interfaces do Spring Data JPA
├── service
│   └── impl            // Implementações da lógica de negócio
├── config              // Classes de configuração do Spring (ex: Swagger)
├── exception           // Exceções customizadas e Handlers globais
└── CatalogApplication.java
```

## 4. Padrões de Design e Decisões

* **DTO (Data Transfer Object):** Utilizaremos DTOs para desacoplar a representação da API da estrutura do banco de dados (Entidades).
* **Múltiplas Representações com `@JsonView`:** Para evitar a "explosão de DTOs", usaremos as anotações `@JsonView` do Jackson para serializar diferentes "visões" de um mesmo DTO (ex: uma visão pública resumida e uma visão interna detalhada) a partir do mesmo endpoint.
* **Camada de Serviço Genérica:** Implementaremos um `AbstractGenericService` para encapsular a lógica de CRUD comum, mantendo os serviços concretos (`ProdutoService`) enxutos e focados em regras de negócio específicas.
* **Logging com `@Slf4j`:** Adotaremos a anotação do Lombok para injeção de loggers, evitando código boilerplate.
* **Migrações com Flyway:** Todas as alterações de schema do banco de dados serão gerenciadas através de scripts SQL versionados na pasta `src/main/resources/db/migration`.
* **[ATUALIZADO] Documentação de API com Springdoc OpenAPI:** Para a documentação automática da API, utilizaremos a dependência `org.springdoc:springdoc-openapi-starter-webmvc-ui`. A versão escolhida é a **`2.5.0`**. Esta versão foi selecionada por ser a versão estável e segura recomendada para o ecossistema Spring Boot 3.x, evitando ativamente versões com vulnerabilidades de segurança conhecidas.

## 5. Estratégia de Testes

A qualidade do código será garantida por uma estratégia de testes robusta:

* **Testes Unitários:** Focados na