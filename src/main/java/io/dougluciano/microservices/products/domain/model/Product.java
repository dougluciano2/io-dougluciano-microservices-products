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

@Entity
@Table(name = "products")
@AllArgsConstructor @NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractFullEntity {

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, unique = true)
    private String sku;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant creationDate;
}
