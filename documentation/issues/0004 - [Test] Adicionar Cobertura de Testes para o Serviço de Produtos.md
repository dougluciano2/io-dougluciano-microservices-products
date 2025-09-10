# `[Test] Adicionar Cobertura de Testes para o Serviço de Produtos`

**Labels Sugeridas:** `test`, `quality`

### Descrição

O objetivo desta tarefa é criar uma suíte de testes automatizados que valide o comportamento de todas as camadas do serviço de produtos. Seguiremos a estratégia definida na arquitetura, com testes unitários para a lógica de negócio e testes de integração para as camadas que interagem com a infraestrutura (banco de dados e web).

### Tarefas a Serem Executadas

#### 1. Testes de Integração da Camada de Repositório
- [ ] Criar a classe de teste `ProdutoRepositoryTest`.
- [ ] Utilizar a anotação `@DataJpaTest` para focar o teste na camada de persistência.
- [ ] Integrar com **Testcontainers** (`@Testcontainers`, `@Container`) para instanciar um banco de dados PostgreSQL real e isolado para os testes.
- [ ] Escrever testes que validem as operações CRUD básicas através do `ProdutoRepository` (salvar, buscar por id, listar todos, deletar).
- [ ] Se houverem queries customizadas no repositório, criar testes específicos para elas.

#### 2. Testes Unitários da Camada de Serviço
- [ ] Criar a classe de teste `ProdutoServiceTest`.
- [ ] Utilizar **Mockito** (`@Mock`, `@InjectMocks`) para criar um teste de unidade puro, sem carregar o contexto do Spring.
- [ ] Mockar o `ProdutoRepository` para simular seu comportamento (`when(...).thenReturn(...)`).
- [ ] Escrever testes que validem as regras de negócio implementadas no `ProdutoService`, garantindo que ele chame os métodos corretos do repositório.

#### 3. Testes de Integração da Camada de Controller
- [ ] Criar a classe de teste `ProdutoControllerTest`.
- [ ] Utilizar a anotação `@SpringBootTest` para carregar o contexto completo da aplicação e o **Testcontainers** para ter um banco de dados real disponível.
- [ ] Utilizar o `MockMvc` para simular requisições HTTP para os endpoints da `ProdutoController`.
- [ ] Escrever testes para cada endpoint (`GET`, `POST`, `PUT`, `DELETE`), validando:
    - Os status codes HTTP retornados (200, 201, 204, 404, etc.).
    - O corpo (`body`) das respostas JSON.
- [ ] **Validar especificamente o funcionamento do `@JsonView`**, utilizando `JsonPath` para afirmar que a lista de produtos (`GET /api/v1/produtos`) contém menos campos que a busca por um produto específico (`GET /api/v1/produtos/{id}`).

### Critérios de Aceitação

* Todas as camadas (Repository, Service, Controller) devem ter testes correspondentes.
* A suíte de testes deve rodar com sucesso, confirmando que toda a funcionalidade de CRUD de produtos está operando como esperado.