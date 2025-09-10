# `[Feature] Definir Entidade Produto e Criar Migração do Banco de Dados`

**Labels:** `database`, `feature`, `setup`

### Descrição

Esta tarefa é o alicerce do serviço. Precisamos definir a estrutura de dados da entidade `Produto` no código através do JPA e criar o script de migração correspondente com o Flyway para que a tabela seja criada fisicamente no banco de dados.

### Tarefas a Serem Executadas

- [ ] Criar a classe `Produto.java` no pacote `io.dougluciano.microservices.products.domain.model`.
- [ ] Anotar a classe com `@Entity` e `@Table(name = "produtos")`.
- [ ] Definir os campos da entidade, considerando os seguintes:
    - `id` (Long, Chave Primária, Autoincremento)
    - `nome` (String, Não nulo)
    - `descricao` (String, Texto longo)
    - `preco` (BigDecimal, Não nulo)
    - `sku` (String, Não nulo, Único)
    - `dataDeCadastro` (Instant, Não nulo)
- [ ] Adicionar as anotações JPA e de validação apropriadas (`@Id`, `@GeneratedValue`, `@Column`, `@NotNull`, etc.).
- [ ] Criar o primeiro script de migração do Flyway no diretório `src/main/resources/db/migration` com o nome `V1__create_table_produtos.sql`.
- [ ] No script de migração, escrever o comando SQL `CREATE TABLE` para a tabela `produtos`, garantindo que os tipos de dados e as constraints (NOT NULL, UNIQUE) correspondam à definição da entidade JPA.