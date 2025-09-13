package io.dougluciano.microservices.products.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

/**
 * DTO Padrão para respostas de erro da API.
 * Fornece uma estrutura consistente para que os clientes possam tratar os erros de forma programática.
 */
@Getter
@Builder
public class ApiErrorResponse {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
