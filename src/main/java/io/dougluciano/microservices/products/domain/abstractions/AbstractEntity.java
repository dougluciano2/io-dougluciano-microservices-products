package io.dougluciano.microservices.products.domain.abstractions;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Superclasse abstrata para todas as entidades persistentes do sistema.
 * <p>
 * O objetivo desta classe é centralizar a definição da chave primária (ID).
 * Todas as entidades do domínio devem herdar desta classe (direta ou indiretamente)
 * para garantir uma estratégia de identificação consistente.
 * <p>
 * A anotação {@code @MappedSuperclass} garante que os campos aqui definidos
 * serão mapeados nas tabelas das classes filhas, mas esta classe em si
 * não se tornará uma tabela no banco de dados.
 *
 * @author dougluciano
 * @since 2025-09-09
 */
@Getter @Setter
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
