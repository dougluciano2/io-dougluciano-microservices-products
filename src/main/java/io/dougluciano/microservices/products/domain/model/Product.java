package io.dougluciano.microservices.products.domain.model;

import io.dougluciano.microservices.products.domain.abstractions.AbstractFullEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Representa a entidade Produto no domínio da aplicação.
 * <p>
 * Esta classe mapeia a tabela {@code products} no banco de dados e contém
 * todas as informações específicas de um produto.
 * <p>
 * Ela herda o campo {@code id} de {@link io.dougluciano.microservices.products.domain.abstractions.AbstractEntity}
 * e os campos de auditoria (createdAt, createdBy, etc.) de
 * {@link io.dougluciano.microservices.products.domain.abstractions.AbstractFullEntity}.
 *
 * @author dougluciano
 * @since 2025-09-09
 */
@Entity
@Table(name = "products")
@AllArgsConstructor @NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractFullEntity {

    /**
     * O nome do produto.
     * <p>
     * <b>Validações:</b>
     * <ul>
     * <li>{@code @NotBlank}: Não pode ser nulo, vazio ou conter apenas espaços em branco.</li>
     * <li>{@code @Size(max = 255)}: Deve ter no máximo 255 caracteres.</li>
     * </ul>
     */
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    /**
     * Descrição detalhada do produto. Campo opcional e sem limite de caracteres definido pela aplicação.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * O preço de venda do produto.
     * <p>
     * <b>Validações:</b>
     * <ul>
     * <li>{@code @NotNull}: Não pode ser nulo.</li>
     * <li>{@code @Positive}: Deve ser um valor estritamente maior que zero.</li>
     * </ul>
     */
    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * SKU (Stock Keeping Unit). Um código de barras ou identificador único para o produto.
     * <p>
     * <b>Validações:</b>
     * <ul>
     * <li>{@code @NotBlank}: Não pode ser nulo, vazio ou conter apenas espaços em branco.</li>
     * <li>{@code @Size(max = 100)}: Deve ter no máximo 100 caracteres.</li>
     * </ul>
     */
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, unique = true)
    private String sku;

}
