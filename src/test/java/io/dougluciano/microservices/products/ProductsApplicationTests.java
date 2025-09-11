package io.dougluciano.microservices.products;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * Teste de integração principal da aplicação.
 * <p>
 * A anotação {@code @SpringBootTest} inicia o contexto completo da aplicação Spring,
 * simulando o comportamento real da aplicação.
 * <p>
 * A anotação {@code @Import(TestcontainersConfiguration.class)} instrui este teste a
 * carregar a configuração de container que definimos, garantindo que a aplicação
 * inicie conectada a um banco de dados PostgreSQL real e isolado.
 *
 * @author Spring Initializr / dougluciano
 * @since 2025-09-10
 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ProductsApplicationTests {

	/**
	 * Testa se o contexto da aplicação Spring consegue ser carregado com sucesso.
	 * <p>
	 * Este teste é um "teste de fumaça" (smoke test). Se ele passar, significa que
	 * todas as configurações, injeções de dependência e a conexão inicial com o banco
	 * de dados (via Testcontainers) estão funcionando corretamente.
	 */
	@Test
	void contextLoads() {
	}

}
