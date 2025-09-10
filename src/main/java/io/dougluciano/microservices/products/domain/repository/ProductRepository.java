package io.dougluciano.microservices.products.domain.repository;

import io.dougluciano.microservices.products.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
