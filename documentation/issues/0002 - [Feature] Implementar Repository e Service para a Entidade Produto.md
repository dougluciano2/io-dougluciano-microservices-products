# `[Feature] Implementar Repository e Service para a Entidade Produto`

**Labels Sugeridas:** `feature`, `backend`

### Descrição

Com a entidade e a tabela do banco de dados definidas, o próximo passo é criar a camada de acesso a dados (Repository) e a camada de serviço (Service), que conterá a lógica de negócio. Esta tarefa também inclui a implementação do padrão de **Serviço Genérico** que discutimos na arquitetura, visando a reutilização de código e a organização.

### Tarefas a Serem Executadas

- [ ] Criar a interface `GenericService<T, ID>` no pacote `io.dougluciano.microservices.products.service`.
- [ ] Criar a classe abstrata `AbstractGenericService<T, ID>` no pacote `io.dougluciano.microservices.products.service.impl`, implementando a interface `GenericService`. Esta classe conterá a lógica de CRUD comum (findById, findAll, save, deleteById, update).
- [ ] Criar a interface `ProdutoRepository.java` no pacote `io.dougluciano.microservices.products.domain.repository`, fazendo-a estender `JpaRepository<Produto, Long>`.
- [ ] Criar a classe `ProdutoService.java` no pacote `io.dougluciano.microservices.products.service.impl`, fazendo-a estender `AbstractGenericService<Produto, Long>`.
- [ ] Injetar o `ProdutoRepository` no construtor do `ProdutoService` e passá-lo para o construtor da classe pai (`super(produtoRepository)`).

### Critérios de Aceitação

* Ao final desta tarefa, teremos uma camada de serviço funcional, capaz de realizar as operações básicas de CRUD na entidade `Produto`, pronta para ser utilizada pelo Controller na próxima etapa.