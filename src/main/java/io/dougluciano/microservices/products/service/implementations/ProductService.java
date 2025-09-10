package io.dougluciano.microservices.products.service.implementations;

import io.dougluciano.microservices.products.domain.model.Product;
import io.dougluciano.microservices.products.domain.repository.ProductRepository;
import io.dougluciano.microservices.products.service.abstractions.AbstractGenericService;
import org.springframework.stereotype.Service;

/**
 * Implementação concreta do serviço para gerenciar a entidade {@link Product}.
 * <p>
 * Esta classe herda toda a funcionalidade de CRUD (Create, Read, Update, Delete)
 * da sua superclasse {@link AbstractGenericService}, que contém a lógica comum
 * para todas as entidades do sistema.
 * <p>
 * O objetivo principal desta classe é conter lógicas de negócio que são
 * <b>específicas</b> apenas para Produtos e que não se aplicam a outras entidades.
 * Por enquanto, a classe pode parecer vazia, pois todas as operações básicas
 * já estão implementadas na classe pai.
 * <p>
 * <b>Observação de Design Importante:</b>
 * Esta classe <b>não</b> declara seu próprio campo de repositório. Ela utiliza o campo
 * {@code protected final JpaRepository<Product, Long> repository} herdado de
 * {@code AbstractGenericService}, que é inicializado através do construtor abaixo.
 * Isso evita a duplicação de campos ("field shadowing") e mantém o código limpo.
 *
 * @author dougluciano
 * @since 2025-09-09
 */
@Service
public class ProductService extends AbstractGenericService<Product, Long> {

    /**
     * Construtor que realiza a injeção de dependência do {@link ProductRepository}.
     * <p>
     * O repositório específico de {@code Product} é então passado para o construtor
     * da superclasse {@code AbstractGenericService}, que o armazena em seu campo
     * {@code protected} para uso nas operações de CRUD genéricas.
     *
     * @param productRepository o repositório específico para a entidade Product, injetado pelo Spring.
     */
    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }
}
