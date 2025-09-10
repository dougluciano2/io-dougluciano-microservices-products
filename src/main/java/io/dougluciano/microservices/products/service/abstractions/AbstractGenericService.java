package io.dougluciano.microservices.products.service.abstractions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementação abstrata e reutilizável da interface {@link GenericService}.
 * <p>
 * Esta classe contém a lógica de CRUD padrão, utilizando um {@link JpaRepository}
 * genérico para interagir com o banco de dados. O objetivo é evitar a repetição
 * de código nas classes de serviço concretas (ex: {@code ProductService}).
 * <p>
 * As anotações {@code @Transactional} são usadas para gerenciar as transações
 * com o banco de dados, garantindo a consistência dos dados.
 *
 * @param <T> o tipo da entidade.
 * @param <ID> o tipo do ID da entidade.
 *
 * @author dougluciano
 * @since 2025-09-09
 */
public abstract class AbstractGenericService<T, ID>  implements GenericService<T, ID> {

    /**
     * O repositório JPA para a entidade T. É declarado como {@code protected final}
     * para que seja inicializado uma vez pelo construtor e possa ser acessado
     * diretamente pelas subclasses, se necessário, para lógicas mais específicas.
     */
    protected final JpaRepository<T, ID> repository;

    /**
     * Construtor protegido para garantir que apenas classes filhas possam instanciar esta classe.
     * Exige um repositório para fornecer a funcionalidade de acesso a dados.
     *
     * @param repository o JpaRepository específico para a entidade T, que será injetado pela classe filha.
     */
    protected AbstractGenericService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public T update(ID id, T entityToUpdate) {
        /**
         * TODO
         * A lógica será aprimorada na issue de Tratamento de Exceções para
         * lançar um erro caso a entidade com o 'id' fornecido não exista.
          */
        if (repository.findById(id).isPresent()) {
            return repository.save(entityToUpdate);
        }
        // Retornaremos uma exceção aqui no futuro
        return null;
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        /**
         * TODO
         * A lógica será aprimorada na issue de Tratamento de Exceções para
         * verificar se a entidade existe antes de tentar deletar.
         */
        repository.deleteById(id);
    }


}
