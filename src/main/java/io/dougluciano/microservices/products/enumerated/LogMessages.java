package io.dougluciano.microservices.products.enumerated;

/**
 * Enum para padronização de mensagens de log em toda a aplicação.
 * O uso de um enum centralizado garante consistência e facilita a manutenção
 * e busca por logs em ferramentas de observabilidade.
 */
public enum LogMessages {
    // --- MENSAGENS DE REQUISIÇÃO (Nível INFO) ---
    CREATE_REQUEST("Recebida requisição para criar novo produto: {}"),
    UPDATE_REQUEST("Recebida requisição para atualizar produto com ID #{}"),
    DELETE_REQUEST("Recebida requisição para deletar produto com ID #{}."),
    FIND_ALL_REQUEST("Recebida requisição para listar todos os produtos."),
    FIND_BY_ID_REQUEST("Recebida requisição para buscar produto com ID #{}."),

    // --- MENSAGENS DE SUCESSO (Nível INFO) ---
    RESOURCE_CREATED_SUCCESS("Produto criado com sucesso com ID #{}."),
    RESOURCE_UPDATED_SUCCESS("Produto com ID #{} atualizado com sucesso."),
    RESOURCE_DELETED_SUCCESS("Produto com ID #{} deletado com sucesso."),
    RESOURCE_BY_ID_FOUND_SUCCESS("Produto com ID #{} encontrado com sucesso."),
    RESOURCE_FIND_ALL_SUCCESS("Listagem de produtos feita com sucesso."),

    // --- MENSAGENS DE ERRO DO CLIENTE (Nível WARN) ---
    RESOURCE_NOT_FOUND("Recurso do tipo Produto não encontrado para o ID #{}."),
    VALIDATION_ERROR("Erro de validação na requisição: {}"),

    // --- MENSAGENS DE ERRO INTERNO (Nível ERROR) ---
    UNEXPECTED_ERROR("Ocorreu um erro inesperado ao processar a requisição. ID de rastreamento (traceId): {}");

    private final String value;


    LogMessages(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
