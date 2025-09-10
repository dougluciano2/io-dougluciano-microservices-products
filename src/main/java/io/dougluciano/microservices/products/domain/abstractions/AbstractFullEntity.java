package io.dougluciano.microservices.products.domain.abstractions;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


/**
 * Identificador único da entidade.
 * A estratégia {@code GenerationType.IDENTITY} delega a geração do valor
 * para o próprio banco de dados (ex: colunas auto-incrementáveis).
 */
@Getter @Setter
@MappedSuperclass
public abstract class AbstractFullEntity extends AbstractEntity {

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;

    @PrePersist
    public void onPrePersist(){
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.createdBy = "testuser";
    }

    @PreUpdate
    public void onPreUpdate(){
        this.updatedAt = Instant.now();
        this.updatedBy = "testuser";
    }

}
