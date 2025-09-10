package io.dougluciano.microservices.products.api.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.dougluciano.microservices.products.api.views.Views;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO (Data Transfer Object) para a entidade Produto.
 * <p>
 * Esta classe é usada como o contrato de dados para a API, tanto para receber dados
 * em requisições (POST/PUT) quanto para enviar dados em respostas (GET).
 * <p>
 * As anotações de validação garantem a integridade dos dados de entrada, enquanto
 * as anotações @JsonView controlam quais campos são expostos em diferentes contextos de saída.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    /**
     * =======================================================================
     * CAMPOS DA VISÃO PÚBLICA ({@link Views.Publico})
     * =======================================================================
     * <p>
     * Os campos abaixo representam a visão mais básica e essencial de um produto.
     * São informações seguras para serem expostas em listagens públicas,
     * catálogos ou qualquer contexto onde apenas um resumo do produto é necessário.
     */
    @JsonView(Views.Publico.class)
    private Long id;

    @JsonView(Views.Publico.class)
    @NotBlank(message = "O nome do produto não pode ser vazio ou nulo.")
    @Size(max = 255)
    private String name;

    @JsonView(Views.Publico.class)
    private String description;

    @JsonView(Views.Publico.class)
    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser um valor positivo.")
    private BigDecimal price;

    @JsonView(Views.Publico.class)
    @NotBlank(message = "O SKU não pode ser vazio ou nulo.")
    @Size(max = 100)
    private String sku;

    /**
     * =======================================================================
     * CAMPOS DA VISÃO INTERNA ({@link Views.Interno})
     * =======================================================================
     * <p>
     * Os campos abaixo são destinados a contextos mais detalhados ou restritos.
     * Por herança, a visão {@code Interno} já inclui todos os campos da visão
     * {@code Publico}.
     * <p>
     * Estes campos de auditoria são úteis para áreas administrativas, logs ou
     * para exibir o histórico completo de um registro.
     */
    @JsonView(Views.Interno.class)
    private Instant createdAt;

    @JsonView(Views.Interno.class)
    private String createdBy;

    @JsonView(Views.Interno.class)
    private Instant updatedAt;

    @JsonView(Views.Interno.class)
    private String updatedBy;
}
