package io.dougluciano.microservices.products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um recurso específico não é encontrado no sistema.
 * A anotação @ResponseStatus(HttpStatus.NOT_FOUND) é um fallback, mas o tratamento
 * principal será feito no nosso ApiExceptionHandler global.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
