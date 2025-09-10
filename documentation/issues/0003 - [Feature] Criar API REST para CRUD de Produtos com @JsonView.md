# `[Feature] Criar API REST para CRUD de Produtos com @JsonView`

**Labels Sugeridas:** `feature`, `api`, `backend`

### Descrição

O objetivo desta tarefa é construir a "porta de entrada" do nosso serviço: uma API RESTful para gerenciar produtos. Utilizaremos o padrão DTO (Data Transfer Object) para desacoplar a API do nosso modelo de domínio e aplicaremos a estratégia de `@JsonView` para fornecer diferentes níveis de detalhe nas respostas JSON, evitando a criação de múltiplos DTOs.

Para o mapeamento entre a Entidade `Produto` e o `ProdutoDTO`, vamos introduzir a biblioteca **MapStruct**, que gera a implementação do mapper em tempo de compilação, mantendo nosso código limpo.

### Pré-requisito: Adicionar Dependência do MapStruct

Antes de começar, adicione o MapStruct ao seu `pom.xml`:

```xml
<org.mapstruct.version>1.5.5.Final</org.mapstruct.version>

<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>${org.mapstruct.version}</version>
</dependency>

<path>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>${org.mapstruct.version}</version>
</path>
```

### Tarefas a Serem Executadas

- [ ] Criar a classe `Views.java` no pacote `...api.view` para definir as interfaces de visão (ex: `Views.Publico`, `Views.Interno`).
- [ ] Criar a classe `ProdutoDTO.java` no pacote `...api.dto` com os campos que serão expostos pela API.
- [ ] Anotar os campos do `ProdutoDTO` com as anotações `@JsonView` apropriadas, associando cada campo a uma ou mais visões.
- [ ] Criar a interface `ProdutoMapper.java` no pacote `...api.mapper` e anotá-la com `@Mapper(componentModel = "spring")`.
- [ ] Definir os métodos de mapeamento na interface `ProdutoMapper` (ex: `ProdutoDTO toDTO(Produto produto);`, `Produto toEntity(ProdutoDTO produtoDTO);`).
- [ ] Criar a classe `ProdutoController.java` no pacote `...api.controller` com o path base `/api/v1/produtos`.
- [ ] Injetar `ProdutoService` e `ProdutoMapper` no controller.
- [ ] Implementar os endpoints para o CRUD (`POST`, `GET /`, `GET /{id}`, `PUT /{id}`, `DELETE /{id}`).
- [ ] Utilizar a anotação `@JsonView` nos métodos dos endpoints (`@GetMapping`, `@PostMapping`, etc.) para controlar quais campos do DTO serão retornados em cada operação.

### Critérios de Aceitação

* A API deve estar funcional e responder nos endpoints de CRUD.
* O endpoint de listagem (`GET /`) deve retornar uma visão simplificada dos produtos (ex: `Views.Publico`).
* O endpoint de busca por ID (`GET /{id}`) deve retornar uma visão mais detalhada (ex: `Views.Interno`).
* As operações de criação (`POST`) e atualização (`PUT`) devem aceitar e retornar um DTO detalhado.