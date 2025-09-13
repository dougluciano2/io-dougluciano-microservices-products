package io.dougluciano.microservices.products.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.dougluciano.microservices.products.api.dto.ProductDTO;
import io.dougluciano.microservices.products.api.mapper.ProductMapper;
import io.dougluciano.microservices.products.api.views.Views;
import io.dougluciano.microservices.products.domain.model.Product;
import io.dougluciano.microservices.products.enumerated.LogMessages;
import io.dougluciano.microservices.products.service.implementations.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para o recurso de Produtos.
 * <p>
 * A anotação {@code @RequiredArgsConstructor} do Lombok gera um construtor
 * com os campos {@code final}, que são injetados pelo Spring via
 * injeção de dependência no construtor.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;


    /**
     * Endpoint para listar todos os produtos.
     * Retorna uma visão pública e simplificada dos produtos.
     */
    @GetMapping
    @JsonView(Views.Publico.class)
    public ResponseEntity<List<ProductDTO>> findAll(){

        log.info(LogMessages.FIND_ALL_REQUEST.getValue());
        List<Product> products = productService.findAll();
        List<ProductDTO> dtos = productMapper.toDTOList(products);

        log.info(LogMessages.RESOURCE_FIND_ALL_SUCCESS.getValue());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Endpoint para buscar um produto pelo seu ID.
     * Retorna uma visão interna e detalhada do produto.
     */
    @GetMapping("/{id}")
    @JsonView(Views.Publico.class)
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){

        log.info(LogMessages.FIND_BY_ID_REQUEST.getValue(), id, LocalDateTime.now());
        Product product = productService.findById(id);

        log.info(LogMessages.RESOURCE_BY_ID_FOUND_SUCCESS.getValue(), id);
        // Quando implementarmos o Exception Handler, isso será refatorado
        return ResponseEntity.ok(productMapper.toDTO(product));
    }

    /**
     * Endpoint para criar um novo produto.
     * Aceita um DTO válido e retorna o produto criado com a URL no header 'Location'.
     */
    @PostMapping
    @JsonView(Views.Publico.class)
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO){

        log.info(LogMessages.CREATE_REQUEST.getValue(), productDTO.getName());

        Product toPersist = productMapper.toEntity(productDTO);
        Product persisted = productService.save(toPersist);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persisted.getId())
                .toUri();

        log.info(LogMessages.RESOURCE_CREATED_SUCCESS.getValue(), persisted.getId());
        return ResponseEntity.created(location).body(productMapper.toDTO(persisted));

    }

    /**
     * Endpoint para atualizar um produto existente.
     */
    @PutMapping("/{id}")
    @JsonView(Views.Publico.class)
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {

        log.info(LogMessages.UPDATE_REQUEST.getValue(), id);

        Product existingProduct = productService.findById(id);

        productMapper.updateEntityFromDTO(productDTO, existingProduct);

        Product updated = productService.update(id, existingProduct);

        log.info(LogMessages.RESOURCE_UPDATED_SUCCESS.getValue(), id);

        return ResponseEntity.ok(productMapper.toDTO(updated));
    }

    /**
     * Endpoint para deletar um produto pelo seu ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info(LogMessages.DELETE_REQUEST.getValue(), id);

        productService.deleteById(id);

        log.info(LogMessages.RESOURCE_DELETED_SUCCESS.getValue(), id);
        return ResponseEntity.noContent().build();
    }
}
