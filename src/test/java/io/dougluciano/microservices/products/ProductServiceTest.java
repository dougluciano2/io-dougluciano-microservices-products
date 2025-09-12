package io.dougluciano.microservices.products;

import io.dougluciano.microservices.products.domain.model.Product;
import io.dougluciano.microservices.products.domain.repository.ProductRepository;
import io.dougluciano.microservices.products.service.implementations.ProductService;
import io.dougluciano.microservices.products.util.ProductTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Teste unitário para a classe ProductService.
 * Utiliza o Mockito para isolar a camada de serviço da camada de persistência,
 * permitindo testar a lógica de negócio de forma independente.
 */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Nested
    class FindByIdTests{

        @Test
        @DisplayName("Deve retornar um produto quando o ID existe")
        void shouldReturnProductWhenIdExists(){
            /**
             * Aqui eu preparo o objeto a ser testado
             */
            Long productId = 1L;
            Product mockProduct = ProductTestFactory.aValidProduct();
            mockProduct.setId(productId);

            /**
             * Já aqui estou instruindo o mock para que, quando o método findById com ID 1 for chamado
             * finja que encontrou no banco e retorne o produto mockado
             */
            when(repository.findById(productId)).thenReturn(Optional.of(mockProduct));

            Optional<Product> productFound = service.findById(productId);

            /**
             * Fase de verificação
             */
            assertThat(productFound).isPresent();
            assertThat(productFound.get().getId()).isEqualTo(productId);

            verify(repository, times(1)).findById(productId);
        }

        @Test
        @DisplayName("Deve retornar vazio quando o ID não existe")
        void shouldReturnEmptyWhenIdDOesNotExist(){
            Long productId = 99L;

            when(repository.findById(productId)).thenReturn(Optional.empty());

            Optional<Product> productFound = service.findById(productId);

            assertThat(productFound).isEmpty();
            verify(repository, times(1)).findById(productId);
        }
    }

    @Nested
    class FindAllTests{
        @Test
        @DisplayName("Deve retornar uma lista de produtos")
        void shouldReturnAllProducts(){

            List<Product> modkProductList = ProductTestFactory.aValidListOfProducts();
            
            when(repository.findAll()).thenReturn(modkProductList);

            List<Product> products = service.findAll();

            assertThat(products).isNotNull();
            assertThat(products).hasSize(2);
            verify(repository, times(1)).findAll();
        }
    }

    @Nested
    class SaveTests{
        @Test
        @DisplayName("Deve salvar um produto com sucesso e retornar a entidade salva")
        void shouldSaveAndReturnProduct(){
            Product productToSave = ProductTestFactory.aValidProduct();
            Product savedProduct = ProductTestFactory.aValidProduct();
            savedProduct.setId(1L);

            when(repository.save(productToSave)).thenReturn(savedProduct);

            Product result = service.save(productToSave);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
            verify(repository, times(1)).save(productToSave);
        }
    }

    @Nested
    class UpdateTests{
        @Test
        @DisplayName("Deve atualizar o produto com sucesso quando o ID é válido e o SKU não é alterado")
        void souldUpdateSuccessfullyWhenIdIsValidAndSkuIsUnchanged(){
            Long productID = 1L;
            Product existingProduct = ProductTestFactory.aValidProduct();
            existingProduct.setId(productID);

            Product productWithChanges = new Product();
            productWithChanges.setName("Nome atualizado");
            productWithChanges.setPrice(new BigDecimal("99.99"));
            productWithChanges.setSku(existingProduct.getSku());

            when(repository.findById(productID)).thenReturn(Optional.of(existingProduct));
            when(repository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

            Product updated = service.update(productID, productWithChanges);

            assertThat(updated).isNotNull();
            assertThat(updated.getName()).isEqualTo("Nome atualizado");
            assertThat(updated.getPrice()).isEqualByComparingTo("99.99");

            verify(repository, times(1)).findById(productID);
            verify(repository, times(1)).save(existingProduct);
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar atualizar um produco com ID inválido")
        void shouldThrowExceptionWhenUpdatingWithInvalidId(){
            Long invalidId = 99L;
            Product productWithChanges = ProductTestFactory.aValidProduct();

            when(repository.findById(invalidId)).thenReturn(Optional.empty());

            assertThrows(RuntimeException.class, () ->{
                service.update(invalidId, productWithChanges);
            });

            verify(repository, never()).save(any(Product.class));
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar alterar o SKU de um produto")
        void shouldThrowExceptionWhenTryingToChangeSku(){
            Long productId = 1L;
            Product existingProduct = ProductTestFactory.aValidProduct();
            existingProduct.setId(productId);

            Product productWithChangedSku = ProductTestFactory.aValidProduct();
            productWithChangedSku.setSku("SKU-ALTERADO-99");

            when(repository.findById(productId)).thenReturn(Optional.of(existingProduct));

            assertThrows(IllegalArgumentException.class, () -> {
               service.update(productId, productWithChangedSku);
            });

            verify(repository, never()).save(any(Product.class));
        }
    }

    @Nested
    class DeleteTests{
        @Test
        @DisplayName("Deve chhamar o método deleteById do repositorio quando o ID é válido")
        void shouldCallRepositoryDeleteById(){
            Long productId = 1L;

            doNothing().when(repository).deleteById(productId);

            service.deleteById(productId);

            verify(repository, times(1)).deleteById(productId);
        }
    }
}
