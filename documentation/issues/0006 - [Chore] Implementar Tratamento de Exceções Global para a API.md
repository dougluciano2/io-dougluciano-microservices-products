# `[Chore] Implementar Tratamento de Exceções Global para a API`

**Labels Sugeridas:** `chore`, `enhancement`, `api`, `error-handling`

### Descrição

Atualmente, se um erro ocorre (como um produto não encontrado ou uma falha de validação), a API pode retornar um stack trace completo do Java, expondo detalhes internos da aplicação e fornecendo uma péssima experiência para o cliente.

O objetivo desta tarefa é implementar um handler de exceções global (`@RestControllerAdvice`) que intercepte exceções lançadas pela aplicação e as traduza em respostas de erro JSON padronizadas e significativas.

### Tarefas a Serem Executadas

1.  **Criar DTO Padrão para Erros:**
    - [ ] No pacote `...api.dto`, criar a classe `ApiErrorResponse.java`.
    - [ ] Adicionar campos a este DTO para padronizar as respostas de erro, como: `timestamp` (Instant), `status` (int), `error` (String, ex: "Not Found"), e `message` (String, a mensagem da exceção).

2.  **Criar Exceção de Negócio Customizada:**
    - [ ] No pacote `...exception`, criar a classe `ResourceNotFoundException.java`, que estende `RuntimeException`. Ela será usada quando uma entidade (como um Produto) não for encontrada.

3.  **Implementar o Handler Global:**
    - [ ] No pacote `...api.exceptionhandler`, criar a classe `ApiExceptionHandler.java` e anotá-la com `@RestControllerAdvice`.
    - [ ] Criar um método para tratar a `ResourceNotFoundException`. Este método deve ser anotado com `@ExceptionHandler(ResourceNotFoundException.class)` e `@ResponseStatus(HttpStatus.NOT_FOUND)`, e deve retornar nosso `ApiErrorResponse`.
    - [ ] Criar um método para tratar erros de validação (`MethodArgumentNotValidException`). Este deve ser anotado com `@ExceptionHandler` e `@ResponseStatus(HttpStatus.BAD_REQUEST)`. A mensagem de erro deve informar quais campos falharam na validação.
    - [ ] (Opcional) Criar um método genérico para tratar qualquer outra `Exception`, retornando um status `500 Internal Server Error`.

4.  **Refatorar a Camada de Serviço:**
    - [ ] Alterar os métodos no `ProdutoService` (como `findById` e `update`) para que, em vez de retornarem um `Optional` vazio, eles lancem a `ResourceNotFoundException` quando um produto não for encontrado. Ex: `.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado..."))`.

### Critérios de Aceitação

* Ao tentar buscar um produto com um ID inexistente (`GET /api/v1/produtos/999`), a API deve retornar um status `404 Not Found` com o corpo do JSON padronizado em `ApiErrorResponse`.
* Ao enviar dados inválidos em uma requisição `POST` ou `PUT` (ex: nome em branco), a API deve retornar um status `400 Bad Request` com uma mensagem clara sobre o erro de validação.
* Nenhuma outra exceção não tratada deve vazar um stack trace para o cliente.