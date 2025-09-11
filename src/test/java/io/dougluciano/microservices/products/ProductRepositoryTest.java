package io.dougluciano.microservices.products;

import io.dougluciano.microservices.products.domain.model.Product;
import io.dougluciano.microservices.products.domain.repository.ProductRepository;
import io.dougluciano.microservices.products.util.ProductTestFactory;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste de integração para o ProductRepository.
 * Utiliza a configuração automática do Spring Boot com Testcontainers e
 * um script SQL pela anotação @Sql para limpar a base de dados antes de cada teste.
 * <b>[ATENÇÃO]</b> a classe Assertions dos pacotes junit e assertj foram importada como estática, não é necessário instanciá-la
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@Sql(scripts = "/sql/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Nested
    class WhenTryingToGet{

        @Test
        @DisplayName("Deve retornar uma lista vazia")
        void shouldReturnAndEmptyList(){
            List<Product> products = productRepository.findAll();

            assertThat(products).isNotNull();
            assertThat(products).isEmpty();
        }

        @Test
        @DisplayName("Deve retornar uma lista de todos os produtos se existirem objetos")
        void shouldReturnAllProductsWhenTheyExist(){
            productRepository.saveAll(ProductTestFactory.aValidListOfProducts());

            List<Product> products = productRepository.findAll();

            assertThat(products).hasSize(2);
        }

        @Test
        @DisplayName("Deve encontrar um produto pelo seu ID após salvá-lo")
        void shouldFindProductByIdAfterSaving(){
            Product toPersist = ProductTestFactory.aValidProduct();
            Product persisted = productRepository.save(toPersist);
            Long productId = persisted.getId();

            Optional<Product> foundProductOpt = productRepository.findById(productId);

            assertThat(foundProductOpt).isPresent();
            assertThat(foundProductOpt.get().getId()).isEqualTo(productId);
            assertThat(foundProductOpt.get().getSku()).isEqualTo("SKU-VALIDO-01");
        }

        @Test
        @DisplayName("Deve lançar uma exceção ao tentar buscar por um ID inválido")
        void shouldNotFindProductWithInvalidId(){

            Long invalidId = 999L;

            Optional<Product> foundProductOpt = productRepository.findById(invalidId);

            assertThat(foundProductOpt).isEmpty();
        }
    }

    @Nested
    class WhenTryingToSave{
        @Test
        @DisplayName("Deve salvar um novo produto com sucesso quando os dados são válidos")
        void shouldSaveProductSuccessfully(){
            Product toPersist = ProductTestFactory.aValidProduct();

            Product persisted = productRepository.save(toPersist);

            assertThat(persisted).isNotNull();
            assertThat(persisted.getId()).isEqualTo(1L);
            assertThat(persisted.getName()).isEqualTo("Produto Válido");
            assertThat(persisted.getCreatedAt()).isNotNull();
        }

        @Test
        @DisplayName("Não deve salvar um produto com o mesmo SKU")
        void shouldNotSaveDupicatedSku(){
            Product toPersist = ProductTestFactory.aValidProduct();

            productRepository.save(toPersist);

            Product duplicatedProduct = Product.builder()
                    .name("Teclado gamer sem fio Azul")
                    .description("Teclado mecânico compacto e com conexão 2.4Ghz")
                    .price(new BigDecimal("599.99"))
                    .sku(toPersist.getSku())
                    .build();

            assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () ->{
                productRepository.saveAndFlush(duplicatedProduct);
            });
        }

        @Test
        @DisplayName("Deve lançar a exceção ao tentar salvar um produto com nome em branco")
        void shouldNotSaveWithBlankName(){
            Product blankNameProduct = ProductTestFactory.aProductWithBlankName();

            assertThrows(ConstraintViolationException.class, () -> {
               productRepository.saveAndFlush(blankNameProduct);
            });
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar um produto com nome acima do limite de 255 caracteres")
        void shouldNotSaveWithNameOverLimit(){
            Product longNameProduct = ProductTestFactory.aProductWithLongName();

            assertThrows(ConstraintViolationException.class, () -> {
                productRepository.saveAndFlush(longNameProduct);
            });
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar um produto nulo")
        void shouldNotSaveNullProduct(){
            Product nullProduct = new Product();

            assertThrows(ConstraintViolationException.class, () -> {
                productRepository.saveAndFlush(nullProduct);
            });
        }
    }

    @Nested
    class WhenTryingToUpdate{

        @Test
        @DisplayName("Deve atualizar um produto com sucesso")
        void shouldUpdateProductSeccessfully(){
            Product originalProduct = productRepository.save(ProductTestFactory.aValidProduct());
            Long productId = originalProduct.getId();
            Instant initialCreationDate = originalProduct.getCreatedAt();

            Product productToBeUpdate = productRepository.findById(productId).orElseThrow();

            productToBeUpdate.setName("Nome atualizado");
            productToBeUpdate.setPrice(new BigDecimal("299.99"));

            Product updatedProduct = productRepository.save(productToBeUpdate);

            assertThat(updatedProduct).isNotNull();
            assertThat(updatedProduct.getName()).isEqualTo("Nome atualizado");
            assertThat(updatedProduct.getPrice()).isEqualByComparingTo("299.99");
            assertThat(updatedProduct.getCreatedAt()).isEqualTo(initialCreationDate);
            assertThat(updatedProduct.getUpdatedAt()).isAfterOrEqualTo(initialCreationDate);
        }

        @Test
        @DisplayName("Deve lançar uma exceção ao tentar atualizar um produto para um SKU já existente")
        void shouldThrowExceptionWhenUpdatingToDuplicateSku(){
            Product productA = productRepository.save(ProductTestFactory.aValidProduct());
            Product productB = productRepository.save(ProductTestFactory.anotherValidProduct());

            Product productToBeUpdated = productRepository.findById(productB.getId()).orElseThrow();
            productToBeUpdated.setSku(productA.getSku());

            assertThrows(DataIntegrityViolationException.class, () -> {
               productRepository.saveAndFlush(productToBeUpdated);
            });
        }

        @Test
        @DisplayName("Deve lançar a exceção ao tentar atualizar um produto com nome em branco")
        void shouldNotSaveWithBlankName(){

            Product product = productRepository.save(ProductTestFactory.aValidProduct());
            product.setName("   ");

            assertThrows(ConstraintViolationException.class, () -> {
                productRepository.saveAndFlush(product);
            });
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar um produto com nome acima do limite de 255 caracteres")
        void shouldNotSaveWithNameOverLimit(){
            Product product = productRepository.save(ProductTestFactory.aValidProduct());

            Long productId = product.getId();

            Product longNameProduct = ProductTestFactory.aProductWithLongName();

            longNameProduct.setId(productId);

            assertThrows(ConstraintViolationException.class, () -> {
                productRepository.saveAndFlush(longNameProduct);
            });
        }
    }

    @Nested
    class WhenTryingToDelete{
        @Test
        @DisplayName("Deve remover um produto com sucesso")
        void shouldRemoveProductSuccessfully(){
            Product productToDelete = productRepository.save(Product.builder()
                    .name("Produto a ser Deletado")
                    .price(new BigDecimal("10.00"))
                    .sku("DEL-PROD-01")
                    .build());

            Long productId = productToDelete.getId();

            productRepository.deleteById(productId);

            Optional<Product> foundProductOpt = productRepository.findById(productId);
            assertThat(foundProductOpt).isEmpty();
        }

        @Test
        @DisplayName("Não deve lançar uma exceção ao tentar buscar por um ID inválido")
        void shouldNotDeleteAProductWithInvalidId(){

            Long invalidId = 999L;

            // garantir que o banco não está vazio para provar que nada foi deletado
            productRepository.save(ProductTestFactory.aValidProduct());
            Long countBeforeDelete = productRepository.count();

            assertDoesNotThrow(() -> {
                productRepository.deleteById(invalidId);
            });

            Long countAfterDelete = productRepository.count();

            assertThat(countBeforeDelete).isEqualTo(countAfterDelete);

        }
    }
}
