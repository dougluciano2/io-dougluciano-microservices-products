package io.dougluciano.microservices.products.util;

import io.dougluciano.microservices.products.api.dto.ProductDTO;
import io.dougluciano.microservices.products.domain.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe utilitária (Factory) para criar instâncias de Product para uso em testes.
 * Isso centraliza a criação de dados de teste, tornando os testes mais limpos e fáceis de manter.
 */
public final class ProductTestFactory {

    private static final int OVER_255_CHAR = 256;
    private static final String BLANK_VALUE = " ";

    /**
     * Construtor privado para impedir a instância da classe utilitária
     */
    private ProductTestFactory(){}

    /**
     * Cria uma instância de Produto válida e completa.
     * @return um objeto Product pronto para ser salvo.
     */
    public static Product aValidProduct(){
        return Product.builder()
                .name("Produto Válido")
                .description("Descrição de um produto válido.")
                .price(new BigDecimal("199.99"))
                .sku("SKU-VALIDO-01")
                .build();
    }

    /**
     * Cria uma instância de Produto válida e completa para poder ser comparado com outro produto se necessário
     * @return um objeto Product pronto para ser salvo.
     */
    public static Product anotherValidProduct(){
        return Product.builder()
                .name("Outro Produto Válido")
                .description("Outra Descrição de um produto válido.")
                .price(new BigDecimal("299.99"))
                .sku("SKU-VALIDO-02")
                .build();
    }

    /**
     * Cria uma instância de Produto com o nome em branco para testes de validação.
     * @return um objeto Product com o campo nome inválido, violando a constraint @NotBlank
     */
    public static Product aProductWithBlankName() {
        Product product = aValidProduct();
        product.setName(" ");
        return product;
    }

    /**
     * Cria uma instância de Produto com o nome excedendo o limite de caracteres.
     * @return um objeto Product com o campo nome inválido.
     */
    public static Product aProductWithLongName() {
        Product product = aValidProduct();
        product.setName(generateString(OVER_255_CHAR));
        return product;
    }

    /**
     * Cria uma instância de Produto com o preço fora da escala permitida pelo banco.
     * A escala está configurada para precisão de 2 após a casa decimal (10,2)
     * @return um objeto Product com o campo preço inválido para a precisão do banco.
     */
    public static Product aProductWithInvalidPriceScale() {
        Product product = aValidProduct();
        product.setPrice(new BigDecimal("99.999"));
        return product;
    }

    /**
     * Cria um ProductDTO válido
     */
    public static ProductDTO aValidDTOProduct(){
        ProductDTO newProductDto= new ProductDTO();
        newProductDto.setName("Produto Novo Criado");
        newProductDto.setPrice(new BigDecimal("123.45"));
        newProductDto.setSku("SKU-NEW-01");

        return newProductDto;
    }

    /**
     * Cria outro ProductDTO válido
     */
    public static ProductDTO anotherDTOProduct(){
        ProductDTO newProductDto= new ProductDTO();
        newProductDto.setName("Produto Novo Criado 2");
        newProductDto.setPrice(new BigDecimal("123.45"));
        newProductDto.setSku("SKU-NEW-02");

        return newProductDto;
    }


    /**
     * Cria um ProductDTO inválido com nome em branco
     */
    public static ProductDTO aBlankNameDTOProduct(){
        ProductDTO newProductDto= new ProductDTO();
        newProductDto.setName("");
        newProductDto.setPrice(new BigDecimal("123.45"));
        newProductDto.setSku("SKU-NEW-01");

        return newProductDto;
    }


    /**
     * Cria uma lista de 2 produtos válidos
     */
    public static List<Product> aValidListOfProducts(){
        List<Product> products = new ArrayList<>();
        products.add(aValidProduct());
        products.add(Product.builder()
                .name("Outro produto válido")
                .description("Outra descrição válida")
                .price(new BigDecimal("50.00"))
                .sku("SKU-DA-LISTA-01")
                .build());

        return products;
    }

    /**
     * Método auxiliar para gerar strings longas
     * @param length
     * @return
     */
    private static String generateString(int length) {
        return String.join("", Collections.nCopies(length, "A"));
    }


}
