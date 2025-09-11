package io.dougluciano.microservices.products;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
/**
 * Classe de configuração exclusiva para o ambiente de testes.
 * <p>
 * A anotação {@code @TestConfiguration} indica que esta classe contém beans
 * que serão carregados apenas no contexto dos testes do Spring.
 * <p>
 * O objetivo desta classe é definir e configurar os containers do Testcontainers
 * que serão utilizados pelos testes de integração.
 *
 * @author Spring Initializr / dougluciano
 * @since 2025-09-10
 */
@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

	/**
	 * Define o container do PostgreSQL como um Bean do Spring.
	 * <p>
	 * A anotação {@code @Bean} faz com que o Spring gerencie o ciclo de vida deste container.
	 * <p>
	 * A anotação {@code @ServiceConnection} é a "mágica" do Spring Boot 3.1+. Ela
	 * detecta que este bean é um container de banco de dados e automaticamente
	 * configura as propriedades {@code spring.datasource.url}, {@code username} e {@code password}
	 * para se conectarem a este container, eliminando a necessidade de configuração manual
	 * via {@code @DynamicPropertySource}.
	 *
	 * @return uma instância do PostgreSQLContainer que será gerenciada pelo Spring.
	 */
	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
	}

}
