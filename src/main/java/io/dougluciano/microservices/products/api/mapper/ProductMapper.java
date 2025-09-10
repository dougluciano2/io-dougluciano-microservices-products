package io.dougluciano.microservices.products.api.mapper;

import io.dougluciano.microservices.products.api.dto.ProductDTO;
import io.dougluciano.microservices.products.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Interface de mapeamento entre a entidade {@link Product} e seu DTO {@link ProductDTO}.
 * <p>
 * O MapStruct irá gerar uma implementação concreta desta interface em tempo de compilação.
 * A opção {@code componentModel = "spring"} garante que a implementação gerada
 * seja um Bean do Spring e possa ser injetada em outras classes, como os Controllers.
 */

@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Converte uma entidade {@link Product} para um {@link ProductDTO}.
     *
     * @param product a entidade a ser convertida.
     * @return o DTO correspondente.
     */
    ProductDTO toDTO(Product product);

    /**
     * Converte um {@link ProductDTO} para uma entidade {@link Product}.
     *
     * @param productDTO o DTO a ser convertido.
     * @return a entidade correspondente.
     */
    Product toEntity(ProductDTO productDTO);

    /**
     * Converte uma lista de entidades {@link Product} para uma lista de {@link ProductDTO}.
     * O MapStruct automaticamente sabe como usar o método {@code toDTO} para cada item da lista.
     *
     * @param products a lista de entidades a ser convertida.
     * @return a lista de DTOs correspondente.
     */
    List<ProductDTO> toDTOList(List<Product> products);


    /**
     * Atualiza uma entidade {@link Product} existente com os dados de um {@link ProductDTO}.
     * <p>
     * A anotação {@code @MappingTarget} instrui o MapStruct a não criar uma nova instância
     * de {@code Product}, mas sim a atualizar o objeto {@code product} passado como parâmetro.
     * Isso é crucial para atualizações com o JPA, pois preserva o estado da entidade gerenciada.
     * <p>
     * <b>[ATENÇÃO]</b> As anotações {@code @Mapping} com {@code ignore = true} são usadas
     * para proteger campos que não devem ser alterados em uma atualização, como o ID
     * e os campos de data/usuário de criação.
     *
     * @param productDTO o DTO com os dados de origem.
     * @param product    a entidade que será atualizada (o alvo do mapeamento).
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "createdBy", ignore = true)
    })
    void updateEntityFromDTO(ProductDTO productDTO, @MappingTarget Product product);

}
