package io.dougluciano.microservices.products.api.views;

/**
 * Classe contêiner para as interfaces de visão do Jackson (@JsonView).
 * <p>
 * Estas interfaces vazias são usadas como marcadores para controlar quais campos
 * dos DTOs serão serializados em JSON para diferentes endpoints da API.
 * Isso nos permite ter múltiplas representações de um mesmo objeto DTO
 * sem a necessidade de criar classes separadas.
 */
public class Views {

    /**
     * Define a visão **Pública** para a serialização de DTOs.
     * <p>
     * Esta visão deve ser usada para expor apenas os campos essenciais e não sensíveis
     * de um objeto. É ideal para listagens gerais, resumos ou qualquer endpoint
     * que seja acessível publicamente.
     */
    public static class Publico{

    }

    /**
     * Define a visão **Interna** para a serialização de DTOs.
     * <p>
     * Através da herança ({@code extends Publico}), esta visão automaticamente
     * inclui todos os campos marcados com {@code Views.Publico}, além de
     * campos adicionais que são relevantes para contextos mais restritos ou detalhados.
     * <p>
     * É ideal para endpoints que retornam os detalhes completos de um recurso
     * (ex: {@code GET /produtos/{id}}) ou para áreas administrativas do sistema.
     */
    public static class Interno extends Publico{

    }
}
