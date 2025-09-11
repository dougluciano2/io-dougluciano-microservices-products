package io.dougluciano.microservices.products;

import org.springframework.boot.SpringApplication;

/**
 * Classe utilitária para iniciar a aplicação principal em "modo de teste"
 * diretamente a partir da IDE.
 * <p>
 * Ao executar o método {@code main} desta classe, a aplicação {@code ProductsApplication}
 * será iniciada, mas utilizando a configuração definida em {@code TestcontainersConfiguration}.
 * <p>
 * Isso é extremamente útil para desenvolvimento e testes manuais, pois permite
 * rodar a aplicação completa conectada a um banco de dados limpo e temporário
 * em um container Docker, em vez de depender do banco de dados configurado no
 * {@code docker-compose.yml} de desenvolvimento.
 *
 * @author Spring Initializr / dougluciano
 * @since 2025-09-10
 */
public class TestProductsApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProductsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
