package io.dougluciano.microservices.products.service.abstractions;

import java.util.List;
import java.util.Optional;

/**
 * Define o contrato padrão para um serviço genérico de CRUD (Create, Read, Update, Delete).
 * <p>
 * Esta interface utiliza Generics para ser reutilizável com qualquer tipo de entidade
 * do domínio e seu respectivo tipo de identificador.
 * <p>
 * Qualquer classe de serviço que realize operações básicas de persistência deve
 * implementar esta interface para garantir um conjunto consistente de métodos.
 *
 * @param <T> o tipo da entidade do domínio que o serviço gerencia (ex: Product).
 * @param <ID> o tipo do identificador (chave primária) da entidade (ex: Long, UUID).
 *
 * @author dougluciano
 * @since 2025-09-09
 */
public interface GenericService<T, ID> {


    /**
     * Retorna uma lista com todas as entidades do tipo T.
     * @return uma {@link List} contendo todas as entidades.
     */
    List<T> findAll();

    /**
     * Busca uma entidade pelo seu identificador único.
     * @param id o ID da entidade a ser buscada.
     * @return um {@link Optional} contendo a entidade se encontrada, ou um {@code Optional.empty()} caso contrário.
     */
    Optional<T> findById(ID id);

    /**
     * Salva uma nova entidade no banco de dados. Se a entidade já tiver um ID,
     * o provedor JPA pode tratá-la como uma atualização.
     * @param entity a entidade a ser salva.
     * @return a entidade salva, possivelmente com o ID gerado.
     */
    T save(T entity);

    /**
     * Atualiza uma entidade existente, identificada pelo seu ID.
     * @param id o ID da entidade a ser atualizada.
     * @param entityToUpdate o objeto com as informações atualizadas.
     * @return a entidade após a atualização.
     */
    T update(ID id, T entityToUpdate);

    /**
     * Deleta uma entidade do banco de dados pelo seu identificador.
     * @param id o ID da entidade a ser deletada.
     */
    void deleteById(ID id);
}
