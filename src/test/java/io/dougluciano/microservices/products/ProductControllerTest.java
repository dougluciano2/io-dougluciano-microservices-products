package io.dougluciano.microservices.products;

import io.dougluciano.microservices.products.api.dto.ProductDTO;
import io.dougluciano.microservices.products.domain.model.Product;
import io.dougluciano.microservices.products.util.ProductTestFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@Sql(scripts = "/sql/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    private static final String API_URI = "/api/v1/products";
    private static final String API_URI_WITH_PARAMETER = API_URI + "/{id}";
    private static final String VALID_PRODUCT_ID = API_URI + "/1";
    private static final String INVALID_PRODUCT_ID = API_URI + "/999";
    private static final String SEED_SQL_SCRIPT = "/sql/seed-two-products.sql";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Nested
    @DisplayName("Testes para o endpoint GET " + API_URI)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    class GetProductsTests{
        @Test
        @Order(1)
        @DisplayName("Deve retornar a lista de produtos e status 200 (OK)")
        void shouldReturnProductListAndStatus200() throws Exception{

            ProductDTO productDTO1 = ProductTestFactory.aValidDTOProduct();
            ProductDTO productDTO2 = ProductTestFactory.anotherDTOProduct();

            mockMvc.perform(post(API_URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(productDTO1)))
                    .andExpect(status().isCreated());

            mockMvc.perform(post(API_URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(productDTO2)))
                    .andExpect(status().isCreated());


            mockMvc.perform(get(API_URI))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].name", is(productDTO1.getName())))
                    .andExpect(jsonPath("$[1].name", is(productDTO2.getName())))
                    .andExpect(jsonPath("$[0].createdBy").doesNotExist());
        }


        @Test
        @Order(2)
        @DisplayName("Deve retornar lista vazia e status 200 OK quando não há produtos")
        void shouldReturnEmptyListAndStatus200() throws Exception {
            mockMvc.perform(get(API_URI))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(0)));
        }
    }

    @Nested
    @DisplayName("Testes para o endpoint GET " + API_URI_WITH_PARAMETER)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    class GetProductByIdTests {
        @Test
        @Order(3)
        @DisplayName("Deve retornar um produto e status OK quando o ID existir")
        void shouldReturnProductAndStatusOKWhenIdExists() throws Exception{

            ProductDTO productDTO = ProductTestFactory.aValidDTOProduct();

            mockMvc.perform(post(API_URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(productDTO)))
                    .andExpect(status().isCreated());

            mockMvc.perform(get(VALID_PRODUCT_ID))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.createdBy").doesNotExist());
        }

        @Test
        @Order(4)
        @DisplayName("Deve retornar status 404 Not Found quando o ID não existe")
        void shouldReturnStatus404WhenIdDoesNotExist() throws Exception {
            mockMvc.perform(get(INVALID_PRODUCT_ID))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @Order(5)
    @DisplayName("Testes para o endpoint POST " + API_URI)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    class PostProductTests{
        @Test
        @DisplayName("Deve criar um novo produto e retornar status 201 Created")
        void shouldCreateNewProductAndReturnStatus201() throws Exception{
            ProductDTO newProductDto = ProductTestFactory.aValidDTOProduct();

            mockMvc.perform(post(API_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(newProductDto)))
                    .andExpect(status().isCreated())
                    .andExpect(header().exists("Location"))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Produto Novo Criado"))
                    .andExpect(jsonPath("$.createdBy").doesNotExist());

        }

        @Test
        @Order(6)
        @DisplayName("Deve retornar status 400 Bad Request ao tentar criar produto com nome em branco")
        void shouldreturnStatus400WhenCreatingProductWIthBlankName() throws Exception{
            ProductDTO newProductDto = ProductTestFactory.aBlankNameDTOProduct();

            mockMvc.perform(post(API_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(newProductDto)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Testes para o endpoint PUT " + API_URI_WITH_PARAMETER)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    class PutProductTests{
        @Test
        @DisplayName("Deve atualizar um produto e retornar status 200 OK")
        @Sql(SEED_SQL_SCRIPT)
        void shouldUpdateProductAndReturnStatus200() throws Exception{
            ProductDTO updateDto = ProductTestFactory.aValidDTOProduct();
            String jsonRequest = mapper.writeValueAsString(updateDto);

            mockMvc.perform(put(VALID_PRODUCT_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is(updateDto.getName())))
                    .andExpect(jsonPath("$.price", is(updateDto.getPrice().doubleValue())))
                    .andExpect(jsonPath("$.updateddBy").doesNotExist());
        }

        @Test
        @Order(7)
        @DisplayName("Deve retornar status 404 Not Found ao tentar atualizar um ID inexistente")
        void shouldReturnStatus404WhenUpdatingNonExistentProduct() throws Exception{
            ProductDTO updateDto = ProductTestFactory.aValidDTOProduct();
            String jsonRequest = mapper.writeValueAsString(updateDto);

            mockMvc.perform(put(INVALID_PRODUCT_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonRequest))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Testes para o endpoint DELETE " + API_URI_WITH_PARAMETER)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
    class DeleteProductTests{
        @Test
        @Order(8)
        @DisplayName("Deve deletar um produto e retornar status 204 no content")
        void shouldDeleteProductAndReturnStatus204() throws Exception{
            ProductDTO newProductDto = ProductTestFactory.aValidDTOProduct();

            String location = mockMvc.perform(post(API_URI)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(newProductDto)))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getHeader("Location");

            mockMvc.perform(delete(VALID_PRODUCT_ID))
                    .andExpect(status().isNoContent());
            mockMvc.perform(get(VALID_PRODUCT_ID))
                    .andExpect(status().isNotFound());
        }

        @Test
        @Order(9)
        @DisplayName("Deve retornar status 404 Not Found ao tentar deletar um ID inexistente")
        void shouldReturnStatus404WhenDeletingNonExistentProduct() throws Exception{
            mockMvc.perform(delete(INVALID_PRODUCT_ID))
                    .andExpect(status().isNotFound());
        }
    }
}
